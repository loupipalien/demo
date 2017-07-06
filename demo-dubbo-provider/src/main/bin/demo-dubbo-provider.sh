#!/bin/bash

if [ $# -ne 1 ];then
        echo "e.g.:sh $0 start|stop|restart"
        exit
fi

cd `dirname $0`
bin_dir=$(pwd)
project_path=${bin_dir%/*}
lib_dir=${project_path}/lib
conf_dir=${project_path}/conf
pid_file=${project_path}/demo-dubbo-provider.pid
nohup_file=${bin_dir}/nohup.log

#load libs
class_path="${project_path}/demo-dubbo-provider.jar"
for i in `ls ${lib_dir}`
do
    class_path=${lib_dir}/${i}:${class_path}
done

#load properties dir
class_path=${conf_dir}/:${class_path}

function start(){
	echo "${class_path}"
    if [ ! -f "${pid_file}" ]; then
        nohup java -cp ${class_path} com/ltchen/demo/dubbo/provider/ProviderApp >> ${nohup_file} 2>&1 &
        echo $!>> ${pid_file}
    else
         echo "demo-dubbo-provider is running..."
    fi
}
function stop(){
    pid=$(sed -n "1,1p" ${pid_file})
    if [ "${pid}" != "" ];then
        kill -9 ${pid}
        rm -f ${pid_file}
    else
        echo "demo-dubbo-provider is not running..."
        fi
}

if [ $1 == "start" ];then
    start
elif [ $1 == "stop" ];then
    stop
elif [ $1 == "restart" ];then
    stop
    start
else
    echo "e.g.:sh $0 start|stop|restart"
fi