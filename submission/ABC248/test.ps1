function Sample{
	param([int]$n)
	write-output $n
	$n = $n+1
	for($i=0;$i-lt$n-1;$i++){
		$temp = Get-Random $n
		write-output $temp
	}
	$q = 200000
	write-output $q
	for($i=0;$i-lt$q;$i++){
		$l = Get-Random -Maximum 200000
		$r = Get-Random -Maximum 200000 -Minimum $l
		$x = Get-Random -Maximum 200000
		write-output "$l $r $x"
	}
}
Sample 200000 > test.txt
(Measure-Command{cat test.txt | java Main}).TotalMilliseconds