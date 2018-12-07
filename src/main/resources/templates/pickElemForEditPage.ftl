<#import "parts/common.ftl" as c>

<@c.page>
<body>
<h3 align="center">Delete an element:</h3>
<form method="get" action="editElem">
    <div class="addNewInputs">
        <h4>Number:</h4><input type="number" name="numberField" class="editAndDelInp" required>
    </div>
    <button class="addNewButton">Pick</button>
</form>
</body>
</@c.page>