var accountApiAllAccounts = Vue.resource('/account/get-accounts-current-user')
var historyApi = Vue.resource('/history{/id}')

Vue.component('select-account', {
    template: '<select id="acc" style="position: absolute; left: 10%" >' +
        '<option value = "hide" > --Счёт-- </option>' +
        '</select>',
    created: function () {
        accountApiAllAccounts.get().then(result =>
            result.json().then(data =>
                data.forEach(account => {
                    var accLast = document.createElement('option');
                    accLast.innerHTML = '№ ' + account.accountId + ', баланс: ' + account.balance + ' руб.'
                    accLast.setAttribute('value', account.accountId);
                    acc.append(accLast)
                })
            ))
    }
})

Vue.component('history-row', {
    props: ['history', 'histories'],
    data: function () {
        return {}
    },
    template: '<tr>' +
        '<td><i>{{ history.modifiedDate }}</i></td>' +
        '<td>{{ history.balance }} руб.</td>' +
        '<td>{{ history.comment }}</td>' +
        '</tr>'
});
Vue.component('histories-list', {
    props: ['histories'], /*properties*/
    template: '<div>' +
        '<p>Выберите счёт: <select-account /> ' +
        '<input style="position: absolute; left: 25%" type="button" value="Выписка по счёту" @click="view" /></p>' +
        '<p id="createDate"></p>' +
        '<table>' +
        '   <tr>' +
        '       <td style="width: 55%">Дата изменения</td>' +
        '       <td style="width: 20%">Баланс</td>' +
        '       <td>Комментарий</td>' +
        '   </tr>' +
        '   <history-row v-for="history in histories" :key="history.rev" :history="history" :histories="histories" />' +
        '</table>' +
        '</div>',
    methods: {
        view: function () {
            if (acc.value === 'hide') {
                alert("Выберете счёт для выписки")
            } else {
                historyApi.get({id: acc.value}).then(result => {
                        result.json().then(data => {
                                document.getElementById("createDate").innerHTML = "Дата создания: <i>" +
                                    data[0].createdDate + "</i>"
                                data.splice(0, 1)
                                this.histories.splice(0)
                                data.forEach(history => this.histories.push(history))
                            }
                        )
                    }
                )
            }
        }
    }
})

var app = new Vue({
    el: '#view_history',
    template: '<histories-list :histories="histories" />',
    data: {
        histories: []
    }
})