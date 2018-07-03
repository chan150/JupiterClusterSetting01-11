#! /bin/bash
for ((i=2;i<=11;i++))
do
	if [ $i -lt 10 ]; then
		node=jupiter0$i
	else
		node=jupiter$i
	fi
	ssh $node $@ &
done

wait
echo OK.
