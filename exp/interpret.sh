 cat output_exp2 | grep Sort | awk '{print $3}'
 cat output_exp2 | grep Remove | awk '{print $3}'
 cat output_exp2 | grep Write | awk '{print $3}'
 cat output_exp2 | grep RESULT | awk -F= '{print $3}'| awk '{print $1}'
 cat output_exp2 | grep byte | awk '{print $7}'
