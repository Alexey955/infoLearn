<#import "parts/common.ftl" as c>

<@c.page>
    <body class="bodyMain">
        <div>
            <form id="registerForm" action="/registration" method="post">
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            </form>
            <form id="toLoginPage" action="/login" method="get"></form>
            <table class="tableLoginPage">
                <tr class="trLoginPage">
                    <td colspan="2">
                        <div align="center">
                            <b>User Name:</b>
                        </div>
                        <input form="registerForm" class="inpTextLog" type="text" name="username"/>
                        <#if usernameError??><div align="center" class="invalid-feedback">${usernameError}</div></#if>
                    </td>
                </tr>
                <tr class="trLoginPage">
                    <td colspan="2">
                        <div align="center">
                            <b>Password:</b>
                        </div>
                        <input form="registerForm" class="inpTextLog" type="password" name="password"/>
                        <#if passwordError??><div align="center" class="invalid-feedback">${passwordError}</div></#if>
                    </td>
                </tr>
                <tr>
                    <td width="119px">
                        <button form="toLoginPage" class="buttLogPage">Back</button>
                    </td>
                    <td width="119px">
                        <button form="registerForm" class="buttLogPage">Register</button>
                    </td>
                </tr>
            </table>
        </div>
    </body>
</@c.page>