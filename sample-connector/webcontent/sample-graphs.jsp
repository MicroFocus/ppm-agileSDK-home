<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="com.hp.ppm.integration.sdk.JsonUtils" %>
<%@ page import="com.ppm.integration.agilesdk.ValueSet" %>
<%@ page import="com.ppm.integration.agilesdk.pm.JspConstants" %>
<%@ page import="com.ppm.integration.agilesdk.connector.agm.AgmConstants" %>

<%@ include file="/integrationcenter/sdk/include-workplan-integration.jsp" %>
<%
    ValueSet values = (ValueSet) request.getAttribute(JspConstants.WORKPLAN_INTEGRATION_VALUE_SET);
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="x-ua-compatible" content="IE=edge" />
        <title lang="def">AGM_CHARTS</title>
        <link rel="stylesheet" type="text/css" href="/itg/web/new/css/nv.d3.css" />
        <link rel="stylesheet" type="text/css" href="/itg/web/new/css/main.css" />
        <style>
        body,html {
            font-family:arial;
            font-size:12px;
        }

        .graph {
            width:50%;
            height:50%;
            float:left;
            position:relative;
            background-color:white;
            display:none;
        }

        .graphs-2 .graph {
            width:50%;
            height:100%;
        }
		.graph-svg svg{
			width:100%;
            height:100%;
		}
        .graphs-1 .graph {
            width:100%;
            height:100%;
        }

        .graph .graph-svg {
            position:absolute;
            top:40px;left:0;right:0;bottom:0;
            overflow:hidden;
			text-align: center;
        }
        .graph .graph-title,
        .graph .graph-desc {
            line-height:20px;
            height:20px;
            text-overflow:ellipsis;
            overflow:hidden;
            text-align:center;
        }
        .graph .graph-title {
            font-weight:bold;
        }

        .graph .error-msg {
            position:absolute;
            top:0;
            left:0;
            width:100%;
            height:100%;
        }
        .graph .error-msg td {
            vertical-align: middle;
            text-align:center;
        }

        #fallback {
            position:absolute;
            top:0;left:0;
            height:100%;
            width:100%;
        }
        #fallback td {
            text-align:center;
            vertical-align: middle;
        }
        </style>
    </head>
<body>

<div class="graph" id="graph-theme-status">
    <div class="graph-title"></div>
    <div class="graph-desc"></div>
    <div class="graph-svg"><svg></svg></div>
</div>
<div class="graph" id="graph-feature-status">
    <div class="graph-title"></div>
    <div class="graph-desc"></div>
    <div class="graph-svg"><svg></svg></div>
</div>
<div class="graph" id="graph-sprint-burndown">
    <div class="graph-title"></div>
    <div class="graph-desc"></div>
    <div class="graph-svg"><svg></svg></div>
</div>
<div class="graph" id="graph-release-burnup">
    <div class="graph-title"></div>
    <div class="graph-desc"></div>
    <div class="graph-svg"><svg></svg></div>
</div>


<script type="text/javascript" src="/itg/web/new/js/jquery-1.10.2.js"></script>
<script type="text/javascript" src="/itg/web/new/js/ng-common.js"></script>
<script type="text/javascript" src="/itg/web/i18n/wb/KNTA_Resources+web/CoreResources+web/NewUI+web/IntegrationResources?jsonp=ng.i18n"></script>
<script type="text/javascript" src="/itg/web/new/js/d3.v2.js"></script>
<script type="text/javascript" src="/itg/web/new/js/nv.d3.min.js"></script>
<script type="text/javascript" src="/itg/integrationcenter/js/integration-common.js"></script>
<script type="text/javascript">

var graphs = [
    {
        url:'agm-graphs-data-release-burnup.jsp',
        enabled:<%=values.getBoolean(AgmConstants.KEY_SHOW_RELEASE_BURN_UP,false)?"true":"false"%>,
        elementId:'graph-release-burnup',
        fn:function(graphData){
            drawLineGraph(new DataStream(graphData)
                .resetFuture()
                .cleanFirstEmptyRow()
                .get(),'graph-release-burnup');
        }
    },
    {
        url:'agm-graphs-data-sprint-burndown.jsp',
        enabled:<%=values.getBoolean(AgmConstants.KEY_SHOW_SPRINT_BURN_DOWN, false)?"true":"false"%>,
        elementId:'graph-sprint-burndown',
        fn:function(graphData){
            drawLineGraph(new DataStream(graphData)
                .resetFuture()
                .calcCapacity()
                .get(),'graph-sprint-burndown');
        }
    },
    {
        url:'agm-graphs-data-feature-status.jsp',
        enabled:<%=values.getBoolean(AgmConstants.KEY_SHOW_FEATURE_STATUS,false)?"true":"false"%>,
        elementId:'graph-feature-status',
        fn:function(graphData){
            drawBarChart(graphData, 'graph-feature-status');
        }
    },
    {
        url:'agm-graphs-data-theme-status.jsp',
        enabled:<%=values.getBoolean(AgmConstants.KEY_SHOW_THEME_STATUS,  false)?"true":"false"%>,
        elementId:'graph-theme-status',
        fn:function(graphData){
            drawBarChart(graphData, 'graph-theme-status');
        }
    }
];

function showGraphInfo(rawData, elementId){
    var handlers = {
        "GraphName":function(value){
            $('#' + elementId + ' .graph-title').text(getConnectorText(value.replace(/ /g,'_').toUpperCase()));
        },
        "description":function(value){
            $('#' + elementId + ' .graph-desc').text(value);
        },
        "GraphColors":function(value){
            
        }
    };

    ng.forEach(rawData.dataTable.metadataAttributes.attribute,function(i,e){
        (handlers[e.name]||function(){}).call(handlers,e.value);
    });

    $('#' + elementId).css('display','block');

    if((rawData.ret || 0)==0){
        return true;
    }else{
        var msg = $('<table class="error-msg"><tr><td></td></tr></table>');
        msg.find('td').text(getConnectorText(rawData.message));

        $('#' + elementId + ' .graph-svg').empty().append(msg);
        return false;
    } 
}

function DataStream(data){
    this.data = data;
}
DataStream.prototype = {
    get:function(){
        return this.data;
    },
    calcCapacity:function(){

        if((this.data.ret || 0)!=0){
            return this;
        }
        
        var firstRow = this.data.dataTable.rows.row[0], that = this;
        firstRow.name === '' && (function(){
            var sy = parseFloat(firstRow.values.value[0]),
                dy = sy / (that.data.dataTable.rows.row.length - 1);

            ng.forEach(that.data.dataTable.rows.row,function(i,row){
                row.values.value[0] = (sy - dy * i).toFixed(2) + '';
            });

            firstRow.values.value[1] = undefined;
        })();

        
        return this;
    },
    cleanFirstEmptyRow:function(){
        if((this.data.ret || 0)!=0){
            return this;
        }

        var firstRow = this.data.dataTable.rows.row[0], that = this;
	if(firstRow!=undefined){
	        (firstRow.name === ''||firstRow.name === ' ') && (function(){
	            if(firstRow.values.value[0]==='-1.0'){
	                that.data.dataTable.rows.row.shift();
	            }
	        })();
	}
        return this;
    },

    resetFuture:function(){

        if((this.data.ret || 0)!=0){
            return this;
        }

        /* Trim future data */
        var isFuture = false;
        ng.forEach(this.data.dataTable.rows.row,function(i,row){

            if(isFuture){
                row.values.value[0] = undefined;
                row.values.value[1] = undefined;
            }

            row.name === 'Today' && (isFuture = true);
        });

        return this;
    }
};


function drawLineGraph(rawData, elementId){
    showGraphInfo.apply(null,arguments) && nv.addGraph(function(){

        var chart = nv.models.lineChart()
            .x(function(d,i) { return i; })
            .y(function(d) {
                return d[1] < 0 ? 0 : d[1];
            })
            .color(d3.scale.category10().range())
            .useInteractiveGuideline(true)
            ;

        chart.xAxis.tickFormat(function(d) {
            return rawData.dataTable.rows.row[d].name;
        });

        d3.select('#' + elementId + ' svg')
        .datum((function(data){
            var transformed = [];
            ng.forEach(data.dataTable.columns.column,function(seriesIndex,series){
                transformed.push({
                    key:series.name,
                    values:ng.map(data.dataTable.rows.row,function(i,e){
                        var v=parseFloat(e.values.value[seriesIndex]);return [e.name,v]
                    })
                });
            });
            return transformed;
        })(rawData))
        .call(chart);
        chart.noData(noDataAvailable);
        nv.utils.windowResize(chart.update);
        return chart;
    });
}

function drawBarChart(rawData, elementId){
    showGraphInfo.apply(null,arguments) && nv.addGraph(function() {
        var chart = nv.models.multiBarChart()
            .x(function(d,i) { return i; })
            .y(function(d) {
                return d[1] < 0 ? 0 : d[1];
            })
            .stacked(true)
            .transitionDuration(350)
            .reduceXTicks(true)   //If 'false', every single x-axis tick label will be rendered.
            .rotateLabels(0.2)      //Angle to rotate x-axis labels.
            .groupSpacing(0.1)    //Distance between each group of bars.
            .showControls(false)
            .staggerLabels(true)
        ;

        //ng.every(chart,function(k,v){console.log(k)})
		chart.noData(noDataAvailable);
        chart.xAxis.tickFormat(function(d) {
            return rawData.dataTable.rows.row[d].name;
        });

        d3.select('#' + elementId + ' svg')
        .datum((function(data){
            var transformed = [];
            ng.forEach(data.dataTable.columns.column,function(seriesIndex,series){
                transformed.push({
                    key:series.name,
                    values:ng.map(data.dataTable.rows.row,function(i,e){
                        var v=parseFloat(e.values.value[seriesIndex]);return [e.name,v]
                    })
                });
            });
            return transformed;
        })(rawData))
        .call(chart);

        nv.utils.windowResize(chart.update);

        return chart;
    });
}

function processLocalization(data){
    ppmic.i18n(data);
    window.getConnectorText = function(k){
        return ppmic.lc.apply(null,['com.hp.ppm.integration.sass.agm.AGMIntegrationConnector'].concat(Array.prototype.slice.call(arguments)));
    };
};

function processErrorMessage(text){
    
}
var noDataAvailable = '';

true && $(document).ready(function(){
	noDataAvailable = getConnectorText('NO_DATA_AVAILABLE');
    var taskId = ng.getQueryParams()['taskId'];

    var promise = new ng.Promise();

    if(nv.models.multiBarChart===undefined || nv.models.lineChart===undefined){

        ng.texture($('<table id="fallback"><tr><td><a href="javascript:void(0)" onclick="{link}">{text}</a></td></tr></table>'),{
            text:getConnectorText('CLICK_TO_SHOW_CHARTS_IN_NEW_WINDOW'),
            link:'javascript:window.top.open("agm-graphs.jsp?taskId='+taskId+'","newwin")'
        }).appendTo($('body'));
        return;
    }

    var graphCount = 0;
    ng.forEach(graphs, function(i,graph){
        
        if(graph.enabled){
            $.ajax(graph.url + '?taskId=' + taskId)
            .done(function(graphData){
                promise.then(function(p){
                    graph.fn.apply(null,[graphData]);
                    p();    
                });
            })
            .fail(function(jqXHR){
                promise.then(function(p){
                    var msg = $('<table class="error-msg"><tr><td></td></tr></table>');
                    msg.find('td').text(getConnectorText.apply(null,[jqXHR.responseJSON.msg].concat(jqXHR.responseJSON.params)));
                    $('#' + graph.elementId + ' .graph-svg').empty().append(msg);
                    $('#' + graph.elementId).css('display','block');
                    p();
                });
            });

            graphCount++;
        }
    });
    $('body').addClass('graphs-' + graphCount);
});

</script>
<script type="text/javascript" src="/itg/rest2/integration/connector/i18n?jsonp=processLocalization"></script>
</body>
</html>