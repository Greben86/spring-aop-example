Задача!
===
Реализовать свою аннтоацию <font color="#B5B703">@RolesAllowed</font>
Что нужно сделать:
* Проапгрейдить spring приложение до spring boot.
* Отправлять роли пользрвателя в HEADER (пример: X-USER-ROLE: "USER ADMIN")
* Реализовать сrud операции для Personal
* Реализовать свою аннотацию <font color="#B5B703">@RolesAllowed</font>
* В PersonalController ограничить доступ к методам:
    * get, update, post - доступ только у USER и ADMIN
    * delete - доступ только у ADMIN