<#import "parts/common.ftl" as c>

<@c.page>
<body>
    <h3 id="h3EditElem" align="center">Edit an element:</h3>
    <form method="post" action="wallAftEdit">
        <table align="center">
            <tr>
                <td>
                    <h4 class="margineRight">Amount of theses:</h4>
                    <input type="number" name="amountElem" class="inpEditPage margineRight" placeholder=<#if strForEdit??>${strForEdit.amountElem}<#else>"null"</#if> required = ""/>
                    <#if amountElemError??><div align="center" class="invalid-feedback">${amountElemError}</div></#if>
                </td>
                <td>
                    <h4 class="margineLeft">Previous repeat date:</h4>
                    <input type="text" name="datePriorRep" class="inpEditPage margineLeft" placeholder=<#if strForEdit??>${strForEdit.datePriorRep}<#else>"null"</#if> required = ""/>
                    <#if datePriorRepError??><div align="center" class="invalid-feedback">${datePriorRepError}</div></#if>
                </td>
            </tr>
            <tr>
                <td>
                    <h4 class="margineRight">Quantity of mistakes:</h4>
                    <input type="number" name="amountMistakes" class="inpEditPage margineRight" placeholder=<#if qualityFails??>${qualityFails}<#else>"null"</#if> required = ""/>
                    <#if amountMistakesError??><div align="center" class="invalid-feedback">${amountMistakesError}</div></#if>
                </td>
                <td>
                    <h4 class="margineLeft">Next repeat date:</h4>
                    <input type="text" name="dateNextRep" class="inpEditPage margineLeft" placeholder=<#if strForEdit??>${strForEdit.dateNextRep}<#else>"null"</#if> required = ""/>
                    <#if dateNextRepError??><div align="center" class="invalid-feedback">${dateNextRepError}</div></#if>
                </td>
            </tr>
            <tr>
                <td>
                    <h4 class="margineRight">Stage:</h4>
                    <input type="number" name="stage" class="inpEditPage margineRight" placeholder=<#if strForEdit??>${strForEdit.stage}<#else>"null"</#if> required = ""/>
                    <#if stageError??><div align="center" class="invalid-feedback">${stageError}</div></#if>
                </td>
            </tr>
        </table>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button class="addNewButton">Edit</button>
    </form>
</body>
</@c.page>