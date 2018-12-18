<#import "parts/common.ftl" as c>

<@c.page>
<body class="bodyMain">
    <table class="mainTable" align="center">
        <tr class="bordBottBreak trMainTable">
            <td>
                <form action="/logout" method="post">
                    <input type="submit" value="Sign Out" class="buttons"/>
                    <input type="hidden" name="_csrf" value="${_csrf.token}">
                </form>
            </td>
            <td class="bordBottBreak tdFrame" rowspan="5" align="middle">
                <iframe name="mainFrame" class="mainFrameId">Alternative text</iframe>
            </td>
        </tr>
    <tr class="trMainTable">
        <td>
            <form target="mainFrame" action="/showUserList" method="get" id="csrf-form">
                <button class="buttons">Users list:</button>
            </form>
        </td>
    </tr>
    <tr class="trMainTable">
        <td>
            <form target="mainFrame" action="/showFullAvgAccuracy" method="get">
                <button class="buttons">Average accuracy</button>
            </form>
        </td>
    </tr>
    <tr class="trMainTable">
        <td>
            <form target="mainFrame" action="/showFullStageAccuracy" method="get">
                <button class="buttons">Stage accuracy</button>
            </form>
        </td>
    </tr>
    <tr class="trMainTable">
        <td>
            <form method="get">
                <button class="buttons">Clean</button>
            </form>
        </td>
    </tr>
    </table>
</body>
</@c.page>