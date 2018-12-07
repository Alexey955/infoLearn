<#import "parts/common.ftl" as c>

<@c.page>
<body>
    <h3 align="center">Edit an element:</h3>
    <form method="post" action="wallAftEdit">
        <table id="editPageTable" align="center">
            <tr>
                <td>
                    <h4>Amount of theses:</h4><input type="number" name="thesesField" class="inpEditPage"
                                                     placeholder=${DateForPrompt.amountElem} required><br>
                </td>
                <td>
                    <h4>Previous repeat date:</h4><input type="text" name="priorDateField"
                                                         placeholder=${DateForPrompt.datePriorRep} required><br>
                </td>
            </tr>
            <tr>
                <td>
                    <h4>Quantity of mistakes:</h4><input type="number" name="mistakesField" class="inpEditPage"
                                                         placeholder=${qualityFails} required><br>
                </td>
                <td>
                    <h4>Next repeat date:</h4><input type="text" name="nextRepDateField"
                                                     placeholder=${DateForPrompt.dateNextRep} required><br>
                </td>
            </tr>
            <tr>
                <td>
                    <h4>Stage:</h4><input type="number" name="inpStage" class="inpEditPage"
                                          placeholder=${DateForPrompt.stage} required>
                </td>
            </tr>
        </table>
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <button class="addNewButton">Edit</button>
    </form>
</body>
</@c.page>