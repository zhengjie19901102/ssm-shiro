<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>测试shiro认证</title>
</head>
<body>
<form action="${rc.contextPath}/toLogin" method="post">
    <table>
        <tr>
            <td>用户名:</td>
            <td><input type="text" name="username"></td>
        </tr>
        <tr>
            <td>密 码:</td>
            <td><input type="text" name="password"></td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="登 录"></td>
        </tr>
    </table>
</form>
</body>
</html>