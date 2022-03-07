var userApi = Vue.resource('/user')
var userApiUpdate = Vue.resource('/user/update-user')

Vue.component('profile-info', {
    props: ["user"], /*properties*/
    template:
        '<div>' +
        '<p>Логин: {{user.username}}</p>' +
        '<p>Новый пароль: <input style="position: absolute; left: 10%" type="password" v-model="user.password" /></p>' +
        '<p>Имя: <input style="position: absolute; left: 10%" type="text" v-model="user.client.firstname" /></p>' +
        '<p>Фамилия: <input style="position: absolute; left: 10%" type="text" v-model="user.client.surname" /></p>' +
        '<p>Отчество: <input style="position: absolute; left: 10%" type="text" v-model="user.client.patronymic" /></p>' +
        '<p>Дата рождения: <input style="position: absolute; left: 10%" type="date" v-model="user.client.birthday" /></p>' +
        '<p><input type="button" value="Сохранить изменения" @click="edit" /></p>' +
        '</div>',
    created: function () {
        userApi.get().then(result =>
            result.json().then(data => {
                    data.client.birthday = moment(data.client.birthday).format('YYYY-MM-DD')
                    this.user = data
                }
            )
        )
    },
    methods: {
        edit: function () {
            userApiUpdate.update(this.user).then(result =>
                result.json().then(data => {
                    data.client.birthday = moment(data.client.birthday).format('YYYY-MM-DD')
                    this.user = data
                    alert("Изменения сохранены")
                }).catch(exc => {
                    alert("Не удалось сохранить изменения")
                })
            )
        }
    }
})

var app = new Vue({
    el: '#view_prof',
    template: '<profile-info :user="user" />',
    data: {
        user:
            {
                id: '',
                username: '',
                password: '',
                client: {
                    firstname: '',
                    surname: '',
                    patronymic: '',
                    birthday: ''
                }
            }
    }
})