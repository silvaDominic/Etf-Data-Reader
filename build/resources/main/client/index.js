$(document).ready(function(){

    $("#etf_search_form").submit(function(event){

        var $form = $(this);

        var getEtfUrl = $form.attr('action');
        var httpReq = $form.attr('method');
        var onComplete = //Insert charts

        // Check for blank form
        if (task_input == null || task_input == ""){
            alert("Please enter an ETF");
            return false;
        }

        // Prevent form from submitting from browser
        event.preventDefault();

        // Aborts any pending requests
        var request;
        if (request) {
            request.abort();
        }
        // Selects and caches input field
        var $inputs = $form.find("input");

        // Briefly disables input fields during duration of AJAX request
        $inputs.prop("disabled", true);

        // AJAX request for POSTING new task
        ajaxCall(getEtf, httpReq, taskToPost, doneLogic, 'undefined', alwaysLogic);

        //Reenable input fields
        $inputs.prop("disabled", false);
    });

    function ajaxCall(_url, _type, _data, doneHelperFunction, failHelpFunction, alwaysHelperFunction){
        $.ajax({
            url: _url,
            type: _type,
            data: _data
        }).done(function(response, textStatus, jqXHR){
            if (typeof(doneHelperFunction) != 'undefined'){
                doneHelperFunction(response);
            }
            console.log(_type + " successful.");
            console.log("Response: " + response);
            console.log("Text Status: " + textStatus);
            console.log("JQ XMLHttpReq: " + jQuery.parseJSON(jqXHR.responseText));
        }).fail(function(jqXHR, textStatus, errorThrown){
            if (typeof(failHelperFunction) != 'undefined'){
                failHelperFunction(response);
            }
            console.log("Error.");
            console.log("Text Status: " + textStatus);
            console.log("JQ XMLHttpReq: " + jQuery.parseJSON(jqXHR.responseText));
        }).always(function(){
            if (typeof(alwaysHelperFunction) != 'undefined'){
                alwaysHelperFunction();
            }
        });
    });
});
