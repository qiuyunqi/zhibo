function refreshEcharts(title, data) {
    var data0 = data[0] * 1,
        data1 = data[1],
        // data2 = data[2],
        // data3 = data[3],
        minLY,
        maxLY,
        minRY,
        maxRY,
        splitIndex = 0,
        intervalLY = 0,
        intervalRY = 0;
    weekday = ['日', '一', '二', '三', '四', '五', '六'];
    data0 = data0.toFixed(2);
    //生成横坐标时间轴
    var beforenoon = autoTimeline('9:30', '11:30');
    var afternoon = autoTimeline('13:00', '15:00');
    beforenoon.splice(beforenoon.length - 1, 1);
    afternoon[0] = '11:30/13:00';
    var timeline = beforenoon.concat(afternoon);

    maxLY = (Math.max.apply(Math, data1) * 1).toFixed(2);
    minLY = (Math.min.apply(Math, data1) * 1).toFixed(2);
    if ((maxLY - data0) >= (data0 - minLY)) {
        minLY = 2 * data0 - maxLY;
    } else {
        maxLY = 2 * data0 - minLY;
    }
    intervalLY = (maxLY - minLY) / 4;
    minRY = ((Math.min.apply(Math, data1) - data0) / data0 * 100).toFixed(2);
    maxRY = ((Math.max.apply(Math, data1) - data0) / data0 * 100).toFixed(2);
    if ((maxRY - 0) >= (0 - minRY)) {
        minRY = -maxRY;
    } else {
        maxRY = -minRY;
    }
    intervalRY = (maxRY - minRY) / 4;
    // var length2 = data2.length;
    // for (var i = 0; i < length2; i++) {
    //     data2[i] = (data2[i] / 10000).toFixed(2);
    //     data3[i] = (data3[i] / 100000000).toFixed(2);
    // }

    var myChart = echarts.init(document.getElementById('main'));
    //var myChart2 = echarts.init(document.getElementById('main2'));
    option = {
        title: {
            text: title,
            top: '10%',
            left: '10%',
            textStyle: {
                color: '#ABABAB',
                fontSize: 14,
                fontWeight: 'normal'
            }
        },
        graphic: {
            type: 'image',
            id: 'logo',
            right: '11%',
            top: '19%',
            z: -10,
            style: {
                image: '../img/xiaohe.png',
                width: 100,
                height: 30
            }
        },
        legend: {
            data: ['价格'],
            top: '10%'
        },
        tooltip: {
            trigger: 'axis',
            formatter: function(params) {
                var today = new Date();
                todays = today.getFullYear() + '/' + ((today.getMonth() + 1) < 10 ? '0' +
                    (today.getMonth() + 1) : (today.getMonth() + 1)) + '/' + (today.getDate() < 10 ? '0' + today.getDate() : today.getDate());
                //鼠标指向为空时显示为空
                var res = params[0].name && params[0].name.length > 0 ?
                    '<span >' +
                    option.title.text + '<br />' + todays + '/' + weekday[today.getDay()] + ' ' + params[0].name + '<br />' +
                    params[0].seriesName + ': <span style="color:' + setColor((params[0].data - data0)) + '">' + (params[0].data * 1).toFixed(2) + '</span><br />' +
                    '涨跌: <span style="color:' + setColor((params[0].data - data0)) + '">' + (params[0].data - data0).toFixed(2) + '(' + ((params[0].data - data0) / data0 * 100).toFixed(2) + '%)</span>' +
                    '</span>' :
                    '';

                function setColor(data) {
                    return data > 0 ? '#FF0000' : '#00FF00';
                }
                return res;
            }
        },

        // toolbox: {
        //     show: true,
        //     right: '5%',
        //     feature: {
        //         mark: {
        //             show: true
        //         },
        //         saveAsImage: {
        //             show: true
        //         }
        //     }
        // },
        calculable: true,
        xAxis: [{
            type: 'category',
            boundaryGap: false,
            axisLabel: {
                interval: 59,
            },
            splitLine: {
                show: true,
                interval: 29,
                lineStyle: {
                    type: 'dashed'
                }
            },
            axisTick: {
                show: false,
            },
            axisLine: {
                lineStyle: {
                    color: '#ABABAB'
                }
            },
            data: timeline,

        }, {
            type: 'category',
            boundaryGap: false,
            axisTick: {
                show: false,
            },
            axisLine: {
                lineStyle: {
                    color: '#ABABAB'
                }
            },
            data: ''

        }],
        yAxis: [{
                type: 'value',
                scale: true,
                min: minLY,
                max: maxLY,
                splitLine: {
                    show: true,
                    lineStyle: {
                        type: 'dashed'
                    }
                },
                splitNumber: 3,
                interval: intervalLY,
                axisLabel: {
                    formatter: function(val) {
                        return val.toFixed(2);
                    },
                    textStyle: {
                        color: function(val) {
                            val = (val.split(',').join('') * 1).toFixed(2);
                            if (val < data0) {
                                return 'green'
                            } else if (val > data0) {
                                return 'red'
                            }
                        }
                    }
                },
                axisLine: {
                    lineStyle: {
                        color: '#ABABAB'
                    }
                },
                axisTick: {
                    show: false,
                }
            },
            {
                type: 'value',
                scale: true,
                min: minRY,
                max: maxRY,
                splitNumber: 3,
                interval: intervalRY,
                splitLine: {
                    show: false
                },
                axisLine: {
                    lineStyle: {
                        color: '#ABABAB'
                    }
                },
                axisLabel: {
                    formatter: function(val) {
                        return val.toFixed(2) + '%';
                    },
                    textStyle: {
                        color: function(val) {
                            return val.split(',').join('') < 0 ? 'green' : 'red';
                        }
                    }
                },
                axisTick: {
                    show: false,
                }

            }
        ],

        // visualMap: {
        //     show: false,
        //     pieces: [{
        //         gt: 0,
        //         lte: data0,
        //         color: 'green'
        //     }, {
        //         gt: data0,
        //         color: 'red'
        //     }]
        // },
        series: [{
            name: '价格',
            itemStyle: {
                normal: {
                    color: '#5CACEE',
                }
            },
            lineStyle: {
                normal: {
                    width: 1
                }
            },

            animation: false,
            smooth: true,
            type: 'line',
            areaStyle: {
                normal: {
                    color: '#C6E2FF'
                }
            },
            data: data1,
            markLine: {
                silent: true,
                label: {
                    normal: {
                        position: 'start'
                    }
                },
                data: [{
                    yAxis: data0
                }]
            }
        }]
    };

    myChart.setOption(option);


    // option2 = {
    //     tooltip: {
    //         trigger: 'axis'
    //     },
    //     dataZoom: [{
    //         type: 'inside'
    //     }],
    //     calculable: true,
    //     xAxis: [{
    //         type: 'category',
    //         boundaryGap: false,
    //         axisLabel: {
    //             interval: 59,
    //         },
    //         data: timeline,

    //     }],
    //     yAxis: [{
    //             type: 'value',
    //             splitLine: {
    //                 show: false
    //             },
    //             axisLabel: {
    //                 formatter: '{value}'
    //             }
    //         },
    //         {
    //             type: 'value',
    //             splitLine: {
    //                 show: false
    //             },
    //             axisLabel: {
    //                 formatter: '{value} '
    //             }
    //         }
    //     ],
    //     series: [

    //         {
    //             name: '交易次数(万手)',
    //             type: 'bar',
    //             animation: false,
    //             data: data2
    //         },
    //         {
    //             name: '交易总额(亿)',
    //             type: 'line',
    //             animation: false,
    //             lineStyle: {
    //                 normal: {
    //                     width: 1
    //                 }
    //             },
    //             yAxisIndex: 1,
    //             data: data3
    //         }
    //     ]
    // };
    // myChart2.setOption(option2);
    // echarts.connect([myChart, myChart2]);
    window.onresize = function() {
        myChart.resize();
        // myChart2.resize();
    }
}

/**
 * 
 * 
 * @param {any} start 开始时间  (例 9:30)
 * @param {any} end   结束时间  (例 15:00)
 * @returns 
 */
function autoTimeline(start, end) {
    var timeline = [],
        startHour = start.split(':')[0] * 1,
        startMin = start.split(':')[1] * 1,
        endHour = end.split(':')[0] * 1,
        endMin = end.split(':')[1] * 1;
    for (var i = startHour; i <= endHour; i++) {
        var start = (i == startHour) ? startMin : '0';
        var end = (i == endHour) ? endMin : '59';
        for (var j = start; j <= end; j++) {
            j = (j < 10) ? '0' + j : j;
            timeline.push(i + ":" + j);
        }
    }
    return timeline;
}