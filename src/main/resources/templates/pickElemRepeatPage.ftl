<#import "parts/common.ftl" as c>
<#import "parts/myListMacro.ftl" as ml>

<@c.page>
<body>
    <h3 align="center">List of repeating:</h3>
    <@ml.myList listRep>

    </@ml.myList>

    <br><br><br>
    <form method="post" action="wallAftRepeat">
        <div id="pickElemRepeatInp">
            <input class="ElemRepeatInp" type="number" name="inputNum" placeholder="number:" required><br>
            <input class="ElemRepeatInp" type="number" name="inputMistakes" placeholder="mistakes:" required>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <button class="addNewButton">Repeat</button>
    </form>
</body>
</@c.page>