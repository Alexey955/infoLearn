<#import "parts/common.ftl" as c>

<@c.page>
<body class="bodyMain">
<div>
    <form id="loginForm" action="/login" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    </form>
    <form id="registrForm" action="/registration" method="get"></form>

        <table class="tableLoginPage">
            <tr>
                <td colspan="2">
                    <div align="center">
                        <b>User Name:</b>
                    </div>
                    <input form="loginForm" class="inpTextLog" type="text" name="username"/>
                    <br><br>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <div align="center">
                        <b>Password:</b>
                    </div>
                    <input form="loginForm" class="inpTextLog" type="password" name="password"/>
                    <br><br>
                </td>
            </tr>
            <tr>
                <td width="119px">
                    <button form="registrForm" class="buttLogPage">registration</button>
                </td>
                <td width="119px">
                    <button form="loginForm" class="buttLogPage">Sign In</button>
                </td>
            </tr>
        </table>
</div>
</body>
</@c.page>