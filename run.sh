
### 主函数入口
function main() {
	# 循环10min执行一次 如果执行完毕则不再执行
	while(true)
	do
		execute
		sleep 600
	done

}


function execute() {
  Pid=$(jps | grep arbitrage-0.0.1-SNAPSHOT.jar | awk '{print $1}' )
  for i in $Pid
    do
     `kill -9  $i`
  done
  nohup java -jar -Xms4096M -Xmx4096M -Xmn1024M /Users/didi/Downloads/GitHub/arbitrage1/build/libs/arbitrage-0.0.1-SNAPSHOT.jar -D  --huobi.arbitrage.starId=1 --huobi.arbitrage.endId=100 > service.log 2>&1 &
}

main