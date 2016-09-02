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

        // AJAX request info
        var action = $form.attr('action') + etf_input;
        var method = $form.attr('method');
        var data = 'undefined';
        //TODO: Learn more about AmCharts
        var onComplete = function(response){
            var chart;
            //var chartData = response.countryWeights;
            var chartData = '[{"country":"United States","weight":53.14},{"country":"Japan","weight":8.25},{"country":"United Kingdom","weight":6.41},{"country":"Canada","weight":3.23},{"country":"France","weight":3.1},{"country":"China","weight":3.09},{"country":"Germany","weight":2.86},{"country":"Switzerland","weight":2.73},{"country":"Australia","weight":2.62},{"country":"South Korea","weight":1.83},{"country":"Netherlands","weight":1.43},{"country":"Hong Kong","weight":1.42},{"country":"Taiwan","weight":1.37},{"country":"Sweden","weight":1.02},{"country":"Spain","weight":0.79},{"country":"South Africa","weight":0.68},{"country":"Denmark","weight":0.67},{"country":"India","weight":0.62},{"country":"Italy","weight":0.58},{"country":"Brazil","weight":0.5},{"country":"Indonesia","weight":0.47},{"country":"Mexico","weight":0.43},{"country":"Singapore","weight":0.42},{"country":"Belgium","weight":0.4},{"country":"Finland","weight":0.36},{"country":"Russia","weight":0.35},{"country":"Israel","weight":0.34},{"country":"Norway","weight":0.27},{"country":"Turkey","weight":0.18},{"country":"Thailand","weight":0.12},{"country":"Ireland","weight":0.09},{"country":"Hungary","weight":0.08},{"country":"Austria","weight":0.06},{"country":"Philippines","weight":0.05},{"country":"Chile","weight":0.03},{"country":"Peru","weight":0.01},{"country":"Colombia","weight":0.01}]'
            chartData = $.parseJSON(chartData);
            console.log("Country Weights JSON: ", chartData);

            chart = new AmCharts.AmSerialChart();
            chart.dataProvider = chartData;
            chart.categoryField = "Country";

            var graph = new AmCharts.AmGraph();
            graph.title = "Country Weights";
            graph.valueField = "Weight";
            graph.type = "column";
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