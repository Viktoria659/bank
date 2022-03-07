var accountApiAllAccounts = Vue.resource('/account/get-accounts-current-user')

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

Vue.component('selection-view', {
    props: ['id', 'money'],
    data: function () {
        return {
            choice: ''
        }
    },
    template: '<div>' +
        'Выберите операцию <select required name="service" v-model="choice" style="position: absolute; left: 10%" >' +
        '    <option value="plus">Занести деньги на счёт</option>' +
        '    <option value="minus">Снять деньги со счёта</option>' +
        '</select>' +
        '<p>Выберите счёт: <select-account /> </p>' +
        '<p>Сумма: <input min="0" style="position: absolute; left: 10%" type="number" v-model="money" /></p>' +
        '<p><input type="button" id="but" value="Продолжить" @click="pay" /></p>' +
        '</div>',
    methods: {
        pay: function () {
            let path
            if (this.choice === "plus") {
                path = '/account/refill-account-balance/';
            } else if (this.choice === "minus") {
                path = '/account/withdraw-account-balance/';
            }

            if (!this.choice) {
                alert("Выберете операцию, которую собираетесь провести со счётом")
            } else if (acc.value === 'hide') {
                alert("Выберете счёт, с которого желаете взаимодействовать")
            } else if (!this.money || this.money <= 0) {
                this.money = 0
                alert("Введите положительную сумму больше 0")
            } else {
                this.$http.put(path + acc.value + '/' + this.money)
                    .then(result => {
                        alert("Операция проведена успешно")
                    }).catch(exc => {
                    alert("Недостаточно средств на указанном счёте. Операция отклонена")
                })
            }
        }
    }
})

var app = new Vue({
    el: '#operation',
    template: '<selection-view />',
    data: {
        money: ''
    }
})