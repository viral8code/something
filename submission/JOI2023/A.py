N = int(input())

A = []
for _ in range(N):
	A.append(int(input()))

cnt = [0 for _ in range(N)]

s = {}

for i in range(N):
	if A[i] in s.keys():
		m = s[A[i]]
		cnt[m] = i-m
		s[A[i]] = i
		remKey = []
		for key in s.keys():
			if m<s[key]<i:
				remKey.append(key)
		for key in remKey:
			del s[key]
	else:
		s[A[i]] = i

i=0
while i<N:
	if cnt[i] > 0:
		index = i
		for _ in range(cnt[index]):
			print(A[index])
			i = i+1
	else:
		print(A[i])
		i = i+1
