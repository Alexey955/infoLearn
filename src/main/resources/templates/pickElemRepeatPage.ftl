<#import "parts/common.ftl" as c>
<#import "parts/myListMacro.ftl" as ml>

<@c.page>
<body>
    <h3 align="center">List of repeating:</h3>

    <@ml.myList listRep>
    </@ml.myList>

    <br><br><br>
    <form method="post" action="wallAftRepeat">
        <div align="right" id="pickElemRepeatInp">
            <#if numberError??>
                <span class="invalid-feedback">
                ${numberError}
                </span>
            </#if>
            <input class="ElemRepeatInp" type="number" name="number" placeholder="number:" required><br>
            <#if amountMistakesError??>
                <span class="invalid-feedback">
                ${amountMistakesError}
                </span>
            </#if>
            <input  class="ElemRepeatInp" type="number" name="amountMistakes" placeholder="mistakes:" required>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <button class="addNewButton">Repeat</button>
    </form>
</body>
</@c.page>