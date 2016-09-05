$(document).ready(function(){

    $("#etf_search_form").submit(function(event){

        // Cache fields
        var $form = $(this);
        var request;
        var $inputs = $form.find("input");
        var etf_input = document.forms['etf_search_form']['etf'].value;
        console.log("Etf input: ", etf_input);

        // Check for blank form
        if (etf_input == null || etf_input == ""){
            alert("Please enter an ETF");
            return false;
        }

        // Prevent form from submitting from browser
        event.preventDefault();

        // Aborts any pending requests
        if (request) {
            request.abort();
        }

        // Briefly disables input fields during duration of AJAX request
        $inputs.prop("disabled", true);

        //TODO Create chart data for all fields
        // AJAX request info
        var action = $form.attr('action') + etf_input;
        var method = $form.attr('method');
        var data = 'undefined';
        var onComplete = function(response){
            var chart;
            var chartData = response.countryWeights;

            var chart = new AmCharts.AmSerialChart();
            chart.dataProvider = chartData;
            chart.categoryField = "country";

            var graph = new AmCharts.AmGraph();
            graph.valueField = "weight";
            graph.type = "column";
            graph.fillAlphas = 1;
            chart.addGraph(graph);

            chart.write("chartdiv");
        };
        var onFail = 'undefined';
        var onAlways = function() {$inputs.prop("disabled", false);}

        console.log("Action: ", action);
        console.log("Method: ", method);

        // AJAX request for GETTING etf data
        ajaxCall(action, method, data, onComplete, onFail, onAlways);
    });

    function ajaxCall(action, method, data, doneHelperFunction, failHelperFunction, alwaysHelperFunction){
        $.ajax({
            url: action,
            type: method,
            data: data
        }).done(function(response, textStatus, jqXHR){
            if (typeof(doneHelperFunction) != 'undefined'){
                doneHelperFunction(response);
            }
            console.log(method + " successful.");
            console.log("Response: ", response);
            console.log("Text Status: ", textStatus);
            console.log("JQ XMLHttpReq: ", jQuery.parseJSON(jqXHR.responseText));
        }).fail(function(jqXHR, textStatus, errorThrown){
            if (typeof(failHelperFunction) != 'undefined'){
                failHelperFunction(response);
            }
            console.log("Error.");
            console.log("Text Status: ", textStatus);
            console.log("JQ XMLHttpReq: ", jQuery.parseJSON(jqXHR.responseText));
        }).always(function(){
            if (typeof(alwaysHelperFunction) != 'undefined'){
                alwaysHelperFunction();
            }
        });
    }

    function setDataSet(dataset_url){
        AmCharts.loadFile(dataset_url, {}, function(data) {
            chart.dataProvider = AmCharts.parseJSON(data);
            chart.validateData();
        });
    }
});