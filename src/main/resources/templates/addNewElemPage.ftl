<#import "parts/common.ftl" as c>

<@c.page>
<body>
    <form method="post" action="/wallAftAdd">
            <h3 align="center">Fill empty places:</h3>
        <div class="addNewInputs">
            <h4>Number:</h4><input type="number" name="number" required><br>
            <#if numberError??>
                <div align="center" class="invalid-feedback">
                    ${numberError}
                </div>
            </#if>
            <h4>Amount of theses:</h4><input type="number" name="amountElem" required><br>
            <#if amountElemError??>
                <div align="center" class="invalid-feedback">
                    ${amountElemError}
                </div>
            </#if>
            <h4>Quantity of mistakes:</h4><input type="number" name="amountMistakes" required><br>
            <#if amountMistakesError??>
                <div align="center" class="invalid-feedback">
                ${amountMistakesError}
                </div>
            </#if>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <button class="addNewButton">Add</button>
    </form>
</body>
</@c.page>