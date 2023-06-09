recursive subroutine merge_sort(a, n)
  integer, dimension(n) :: a
  integer :: n, i, j, k, m
  integer, dimension(n) :: tmp
  if (n < 2) return
  m = n/2
  call merge_sort(a(1:m), m)
  call merge_sort(a(m+1:n), n-m)
  i = 1
  j = m+1
  do k = 1, n
    if (j > n .or. (i <= m .and. a(i) <= a(j))) then
      tmp(k) = a(i)
      i = i + 1
    else
      tmp(k) = a(j)
      j = j + 1
    end if
  end do
  do k = 1, n
    a(k) = tmp(k)
  end do
end subroutine merge_sort
