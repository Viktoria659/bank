var accountApiAllAccounts = Vue.resource('/account/get-accounts-current-user')
var accountApiAdd = Vue.resource('/account/add-account')
var accountApiDelete = Vue.resource('/account{/id}')

Vue.component('account-row', {
    props: ['account', 'accounts'],
    template: '<div>' +
        'Номер счёта: <i>{{ account.accountId }}</i> ' +
        'Баланс: {{ account.balance }}' +
        '<span style="position: absolute; left: 20%">' +
        '   <input type="button" value="X" @click="del" />' +
        '</span>' +
        '</div>',
    methods: {
        del: function () {
            accountApiDelete.remove({id: this.account.accountId}).then(result => {
                    if (result.ok) {
                        this.accounts.splice(this.accounts.indexOf(this.account), 1)
                    }
                }
            )
        }
    }
});

Vue.component('account-add-form', {
    props: ['accounts'],
    data: function () {
        return {}
    },
    template:
        '<div>' +
        '<input type="button" value="Добавить новый счёт" @click="add" />' +
        '</div>',
    methods: {
        add: function () {
            var account;
            accountApiAdd.save({}, account).then(result =>
                result.json().then(data => {
                    this.accounts.push(data);
                }))
        }
    }
});

Vue.component('accounts-list', {
    props: ['accounts'], /*properties*/
    template: '<div>' +
        '<account-add-form :accounts="accounts" />' +
        '<account-row v-for="account in accounts" :key="account.accountId" :account="account"' +
        ':accounts="accounts" />' +
        '</div>',
    created: function () {
        accountApiAllAccounts.get().then(result =>
            result.json().then(data =>
                data.forEach(account => this.accounts.push(account))
            )
        )
    }
})

var app = new Vue({
    el: '#view_acc',
    template: '<accounts-list :accounts="accounts" />',
    data: {
        accounts: []
    }
})