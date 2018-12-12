<#import "parts/common.ftl" as c>

<@c.page>
<body>
    <h3 id="h3EditElem" align="center">Edit an element:</h3>
    <form method="post" action="wallAftEdit">
        <table <#--id="editPageTable"--> align="center">
            <tr>
                <td>
                    <h4>Amount of theses:</h4>
                    <input type="number" name="amountElem" class="inpEditPage" placeholder=${strForEdit.amountElem} required>
                    <#if amountElemError??>
                        <div class="invalid-feedback">
                        ${amountElemError}
                        </div>
                    </#if>
                </td>
                <td>
                    <h4>Previous repeat date:</h4>
                    <input type="text" name="datePriorRep" placeholder=${strForEdit.datePriorRep} required>
                    <#if datePriorRepError??>
                        <div class="invalid-feedback">
                        ${datePriorRepError}
                        </div>
                    </#if>
                </td>
            </tr>
            <tr>
                <td>
                    <h4>Quantity of mistakes:</h4>
                    <input type="number" name="amountMistakes" class="inpEditPage" placeholder=${qualityFails} required>
                    <#if amountMistakesError??>
                        <div class="invalid-feedback">
                        ${amountMistakesError}
                        </div>
                    </#if>
                </td>
                <td>
                    <h4>Next repeat date:</h4>
                    <input type="text" name="dateNextRep" placeholder=${strForEdit.dateNextRep} required>
                    <#if dateNextRepError??>
                        <div class="invalid-feedback">
                        ${dateNextRepError}
                        </div>
                    </#if>
                </td>
            </tr>
            <tr>
                <td>
                    <h4>Stage:</h4>
                    <input type="number" name="stage" class="inpEditPage" placeholder=${strForEdit.stage} required>
                    <#if stageError??>
                        <div class="invalid-feedback">
                        ${stageError}
                        </div>
                    </#if>
                </td>
            </tr>
        </table>
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <button class="addNewButton">Edit</button>
    </form>
</body>
</@c.page>