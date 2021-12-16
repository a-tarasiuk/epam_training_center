# Тесты для Spring Security

## Тест для успешной авторизации:
```java
@Test
void authTestPositive() {
  given()
    .auth().preemptive().basic("user", "pass")
  .when()
    .get("/")
  .then()
    .statusCode(HttpStatus.SC_OK);
}
```

##
```java
@Test
@WithMockUser(username = "john", roles = { "VIEWER" })
public void givenRoleViewer_whenCallGetUsername_thenReturnUsername() {
    String userName = userRoleService.getUsername();
    
    assertEquals("john", userName);
}
```