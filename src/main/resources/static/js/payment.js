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
    props: ['id', 'money', 'comment'],
    data: function () {
        return {
            choice: ''
        }
    },
    template: '<div>' +
        'Оплатить <select required name="service" v-model="choice" style="position: absolute; left: 10%" >' +
        '    <option value="jkx">ЖКХ</option>' +
        '    <option value="telephone">Телефон</option>' +
        '    <option value="tax">Налоги</option>' +
        '</select>' +
        '<p>Списать со счёта: <select-account /> </p>' +
        '<p>Сумма: <input min="0" style="position: absolute; left: 10%" type="number" v-model="money" /></p>' +
        '<p>Цель: <input style="position: absolute; left: 10%" type="text" placeholder="Необязательно" v-model="comment" /></p>' +
        '<p><input type="button" id="but" value="Оплатить" @click="pay" /></p>' +
        '</div>',
    methods: {
        pay: function () {
            let comment = !this.comment || this.comment === ' ' ? 'нет' : this.comment;

            let path
            if (this.choice === "jkx") {
                path = '/account/pay-for-communal-services/';
            } else if (this.choice === "telephone") {
                path = '/account/pay-for-telephone/';
            } else if (this.choice === "tax") {
                path = '/account/pay-for-tax/';
            }

            if (!this.choice) {
                alert("Выберете услугу, которую желаете оплатить")
            } else if (acc.value === 'hide') {
                alert("Выберете счёт, с которого желаете оплатить")
            } else if (!this.money || this.money <= 0) {
                this.money = 0
                alert("Введите положительную сумму перевода больше 0")
            } else {
                this.$http.put(path + acc.value + '/' + this.money + '/' + comment)
                    .then(result => {
                        alert("Оплата успешно прошла")
                    }).catch(exc => {
                    alert("Недостаточно средств на указанном счёте. Оплата отклонена")
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