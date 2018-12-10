<#import "parts/common.ftl" as c>

<@c.page>
<body class="bodyMain">
    <table class="mainTable" align="center">
        <tr class="bordBottBreak trMainTable">
            <td>
                <button type="button" class="buttons">InfoLearn</button><br>
            </td>
            <td class="bordBottBreak tdFrame" rowspan="7" align="middle">
                <iframe name="mainFrame" class="mainFrameId">Alternative text</iframe>
            </td>
        </tr>
        <tr class="trMainTable">
            <td>
                <form target="mainFrame" action="/showAllList" method="get">
                    <button class="buttons">All list</button>
                </form>
            </td>
        </tr>
        <tr class="trMainTable">
            <td>
                <form target="mainFrame" action="/pickDay" method="get">
                    <button class="buttons">A day list</button>
                </form>
            </td>
        </tr>
        <tr class="trMainTable">
            <td>
                <form target="mainFrame" action="/showAVGAccuracy" method="get">
                    <button class="buttons">Average accuracy</button>
                </form>
            </td>
        </tr>
        <tr class="trMainTable">
            <td>
                <form target="mainFrame" action="/showStageAccuracy" method="get">
                    <button class="buttons">Stage accuracy</button>
                </form>
            </td>
        </tr>
        <tr class="trMainTable">
            <td>
                <form target="mainFrame" action="/showElemInStages" method="get">
                    <button class="buttons">Elements in stages</button>
                </form>
            </td>
        </tr>
        <tr class="trMainTable">
            <td class="bordBottBreak">
                <form action="/userMain" method="get">
                    <button class="buttons">Back</button>
                </form>
            </td>
        </tr>
    </table>
</body>
</@c.page>