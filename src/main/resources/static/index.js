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
            var topTenHoldingsData = {
                content : response.topTenHoldings,
                chartBuildInfo : {
                    titles : {
                        chartTitle : [{"text" : "Top Ten Holdings", "size" : 30}],
                        xAxisTitle : "Company",
                        yAxisTitle : "Weight"
                    },
                    categoryField : 'company',
                    valueField : 'weight',
                    chartType : 'column',
                    htmlId : 'topTenHoldingsChart'
                    }
            }

            var countryWeightsData = {
                content : response.countryWeights,
                chartBuildInfo : {
                    titles : {
                        chartTitle : [{"text" : "Country Weights", "size" : 30}],
                        xAxisTitle : "Country",
                        yAxisTitle : "Weight"
                    },
                    categoryField : 'country',
                    valueField : 'weight',
                    chartType : 'column',
                    htmlId : 'countryWeightsChart'
                    }
            }

            // No data is currently returned for Sector Weights
/*            var sectorWeightsData = {
                content : response.sectorWeights,
                chartBuildInfo : {
                    title : [{"text" : "Sector Weights", "size" : 30}],
                    categoryField : 'sector',
                    valueField : 'weight',
                    chartType : 'column',
                    htmlId : 'sectorWeightsChart'
                    }
            }*/
            createChart(topTenHoldingsData);
            createChart(countryWeightsData);
            //createChart(sectorWeightsData);
        };
        var onFail = 'undefined';
        var onAlways = function() {$inputs.prop("disabled", false);}

        console.log("Action: ", action);
        console.log("Method: ", method);

        // AJAX request for GETTING etf data
        ajaxCall(action, method, data, onComplete, onFail, onAlways);
    });

    // Initialized and creates a chart
    function createChart(dataObj){
        if (dataObj.content === 'undefined') {return;}
        chart = initChart(dataObj);
        chart.write(dataObj.chartBuildInfo.htmlId);
    }

    // Initializes chart using dataObj; returns an initialized chart
    function initChart(dataObj) {
        chart = new AmCharts.AmSerialChart();
        var categoryAxis = chart.categoryAxis;
        var chartData = dataObj.content;

        // Set chart properties
        chart.dataProvider = chartData;
        chart.categoryField = dataObj.chartBuildInfo.categoryField;
        chart.titles = dataObj.chartBuildInfo.titles.chartTile;
        //Set category axis properties
        categoryAxis.title = dataObj.chartBuildInfo.titles.xAxisTitle;
        categoryAxis.autoGridCount = false;
        categoryAxis.gridCount = chartData.length;
        categoryAxis.gridPosition = "start";
        categoryAxis.labelRotation = 90;
        // Set value axes properties
        var valueAxis = new AmCharts.ValueAxis();
        valueAxis.title = dataObj.chartBuildInfo.titles.yAxisTitle;
        chart.addValueAxis(valueAxis);

        // Create new graph and set properties; add to chart
        var graph = new AmCharts.AmGraph();
        graph.valueField = dataObj.chartBuildInfo.valueField;
        graph.type = dataObj.chartBuildInfo.chartType;
        graph.fillAlphas = 1;
        chart.addGraph(graph);

        return chart;
    }

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