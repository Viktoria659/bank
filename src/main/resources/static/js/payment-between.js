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
    props: ['idTo', 'money', 'comment'],
    template: '<div>' +
        '<p>Списать со счёта: <select-account /> </p>' +
        '<p>Перевести на счёт<input min="0" style="position: absolute; left: 10%" type="number" v-model="idTo" /></p>' +
        '<p>Сумма: <input min="0" style="position: absolute; left: 10%" type="number" v-model="money" /></p>' +
        '<p>Цель: <input style="position: absolute; left: 10%" type="text" placeholder="Необязательно" v-model="comment" /></p>' +
        '<p><input type="button" id="but" value="Оплатить" @click="pay" /></p>' +
        '</div>',
    methods: {
        pay: function () {
            let comment = !this.comment || this.comment === ' ' ? 'нет' : this.comment;

            if (acc.value === 'hide') {
                alert("Выберете счёт, с которого желаете оплатить")
            } else if (!this.money || this.money <= 0) {
                this.money = 0
                alert("Введите положительную сумму перевода больше 0")
            } else {
                this.$http.put('/account/transfer-between-accounts/' + acc.value + '/' + this.idTo + '/' +
                    this.money + '/' + comment).then(result => {
                    alert("Перевод успешно выполнен")
                }).catch(exc => {
                    if (exc.status === 404) {
                        alert("Счёт с таким номером не существует. Введите корректный номер счёта для перевода на него")
                    } else {
                        alert("Недостаточно средств на указанном счёте. Перевод отклонён")
                    }
                })
            }
        }
    }
})

var app = new Vue({
    el: '#payment',
    template: '<selection-view />',
    data: {
        money: '',
        comment: ''
    }
})