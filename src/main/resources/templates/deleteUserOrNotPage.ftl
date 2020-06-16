<#import "parts/common.ftl" as c>

<@c.page>
<body>
        <form id="deteteTheUser" action="/deleteTheUser" method="post">
            <input type="hidden" name="_csrf" value="${_csrf.token}">
        </form>
        <form target="_top" id="pickOperatUser" action="/pickOperatUser/${pickedUser.id}" method="get"></form>

        <table id="tableDelUser">
            <tr>
                <td colspan="2">
                    <div align="center">
                        <h3>Are you sure?</h3>
                    </div>
                </td>
            </tr>
            <tr>
                <td width="119px">
                    <button form="deteteTheUser" class="buttLogPage">Yes</button>
                </td>
                <td width="119px">
                    <button form="pickOperatUser" class="buttLogPage">No</button>
                </td>
            </tr>
        </table>
</body>
</@c.page>