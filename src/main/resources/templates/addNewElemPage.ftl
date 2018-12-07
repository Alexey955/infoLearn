<#import "parts/common.ftl" as c>

<@c.page>
<body>
    <form method="post" action="/wallAftAdd">
            <h3 align="center">Fill empty places:</h3>
        <div class="addNewInputs">
            <h4>Number:</h4><input type="number" name="numberField" required><br>
            <h4>Amount of theses:</h4><input type="number" name="thesesField" required><br>
            <h4>Quantity of mistakes:</h4><input type="number" name="mistakesField" required><br>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <button class="addNewButton">Add</button>
    </form>
</body>
</@c.page>