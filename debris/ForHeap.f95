subroutine poll(arr,n,ans)
  implicit none
  integer(8), dimension(n) :: arr
  integer :: n,i
  integer(8) :: ans,tmp
  ans = arr(1)
  arr(1) = arr(n)
  i = 1
  n = n-1
  do
    if(i*2<=n .and. arr(i)>min(arr(2*i),arr(2*i+1)))then
      if(i*2+1<=n .and. arr(2*i)>arr(2*i+1))then
        tmp = arr(2*i+1)
        arr(2*i+1) = arr(i)
        arr(i) = tmp
        i = 2*i+1
      else
        tmp = arr(2*i)
        arr(2*i) = arr(i)
        arr(i) = tmp
        i = 2*i
      end if
    else
      exit
    end if
  end do
end subroutine poll
subroutine add(arr,len,num)
  implicit none
  integer(8), dimension(len) :: arr
  integer :: len,i
  integer(8) :: num
  len = len+1
  i = len
  do
    if(i>1 .and. num<arr(i/2))then
      arr(i) = arr(i/2)
      i = i/2
    else
      arr(i) = num
      exit
    end if
  end do
end subroutine add
