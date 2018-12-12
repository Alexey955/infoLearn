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
            <td class="bordBottBreak tdFrame" rowspan="7" align="middle">
                <iframe name="mainFrame" class="mainFrameId">Alternative text</iframe>
            </td>
        </tr>
        <tr class="trMainTable">
            <td>
                <form target="mainFrame" action="/addNew" method="get" id="csrf-form">
                    <button class="buttons">Add</button>
                </form>
            </td>
        </tr>
        <tr class="trMainTable">
            <td>
                <form target="mainFrame" action="/delElem" method="get">
                    <button class="buttons">Delete</button>
                </form>
            </td>
        </tr>
        <tr class="trMainTable">
            <td>
                <form target="mainFrame" action="/pickElemForEdit" method="get">
                    <button class="buttons">Edit an element</button>
                </form>
            </td>
        </tr>
        <tr class="trMainTable">
            <td>
                <form target="mainFrame" action="/listRep" method="get">
                    <button class="buttons">List for repeating</button>
                </form>
            </td>
        </tr>
        <tr class="trMainTable">
            <td>
                <form action="/pickNeedStat" method="get">
                    <button class="buttons">Status</button>
                </form>
            </td>
        </tr>
        <tr class="trMainTable">
            <td class="bordBottBreak">
                <form method="get">
                    <button class="buttons">Settings</button>
                </form>
            </td>
        </tr>
    </table>
</body>
</@c.page>