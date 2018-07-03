#! /bin/bash
for ((i=2;i<=11;i++))
do
	if [ $i -lt 10 ]; then
		node=jupiter0$i
	else
		node=jupiter$i
	fi
	scp -r $1 $node:$2 &
done

wait
echo OK.
