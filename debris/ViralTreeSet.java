public final class ViralTreeSet {
    private Node root;
    private static final MTRandom rm = new MTRandom();
    private static final int odd = 2;
    private boolean polling;
    private int size;

    private class Node {
        private long value;
        private final ViralTreeSet treeLeft, treeRight;
        private Node left, right;
        private int nodeSize;

        Node( long v ) {
            value = v;
            nodeSize = 1;
            treeLeft = new ViralTreeSet();
            treeRight = new ViralTreeSet();
        }

        boolean add( long x ) {
            if (x == value)
                return false;
            if (rm.nextInt(odd) == 0) {
                final boolean flag;
                if (value > x) {
                    flag = treeLeft.add(x);
                } else {
                    flag = treeRight.add(x);
                }
                if (flag) {
                    nodeSize++;
                    treeRotateCheck();
                }
                return flag;
            } else {
                if (value > x) {
                    if (left == null) {
                        nodeSize++;
                        left = new Node(x);
                    } else {
                        final boolean flag = left.add(x);
                        if (flag) {
                            nodeSize++;
                            rotateCheck();
                        }
                        return flag;
                    }
                } else {
                    if (right == null) {
                        nodeSize++;
                        right = new Node(x);
                    } else {
                        final boolean flag = right.add(x);
                        if (flag) {
                            nodeSize++;
                            rotateCheck();
                        }
                        return flag;
                    }
                }
            }
            return true;
        }

        boolean contains( long x ) {
            if (x == value)
                return true;
            if (value > x) {
                if (treeLeft.contains(x))
                    return true;
                return left != null && left.contains(x);
            } else {
                if (treeRight.contains(x))
                    return true;
                return right != null && right.contains(x);
            }
        }

        void rotateTreeRight() {
            if (left != null) {
                final long v1 = treeLeft.pollLast();
                final long v2 = left.pollLast();
                treeRight.add(value);
                if (v1 > v2) {
                    value = v1;
                    if (polling) {
                        left = new Node(v2);
                        polling = false;
                    } else
                        left.add(v2);
                } else {
                    value = v2;
                    if (polling) {
                        left = new Node(v1);
                        polling = false;
                    } else
                        left.add(v1);
                }
            } else {
                final long v = treeLeft.pollLast();
                treeRight.add(value);
                value = v;
            }
        }

        void rotateTreeLeft() {
            if (right != null) {
                final long v1 = treeRight.pollFirst();
                final long v2 = right.pollFirst();
                treeLeft.add(value);
                if (v1 < v2) {
                    value = v1;
                    if (polling) {
                        right = new Node(v2);
                        polling = false;
                    } else
                        right.add(v2);
                } else {
                    value = v2;
                    if (polling) {
                        right = new Node(v1);
                        polling = false;
                    } else
                        right.add(v1);
                }
            } else {
                final long v = treeRight.pollFirst();
                treeLeft.add(value);
                value = v;
            }
        }

        void rotateRight() {
            if (treeLeft.size() > 0) {
                final long v1 = left.pollLast();
                final long v2 = treeLeft.pollLast();
                if (polling) {
                    left = null;
                    polling = false;
                }
                if (right == null)
                    right = new Node(value);
                else
                    right.add(value);
                if (v1 > v2) {
                    value = v1;
                    treeLeft.add(v2);
                } else {
                    value = v2;
                    treeLeft.add(v1);
                }
            } else {
                final long v = left.pollLast();
                if (polling) {
                    left = null;
                    polling = false;
                }
                if (right == null)
                    right = new Node(value);
                else
                    right.add(value);
                value = v;
            }
        }

        void rotateLeft() {
            if (treeRight.size() > 0) {
                final long v1 = right.pollFirst();
                final long v2 = treeRight.pollFirst();
                if (polling) {
                    right = null;
                    polling = false;
                }
                if (left == null)
                    left = new Node(value);
                else
                    left.add(value);
                if (v1 < v2) {
                    value = v1;
                    treeRight.add(v2);
                } else {
                    value = v2;
                    treeRight.add(v1);
                }
            } else {
                final long v = right.pollFirst();
                if (polling) {
                    right = null;
                    polling = false;
                }
                if (left == null)
                    left = new Node(value);
                else
                    left.add(value);
                value = v;
            }
        }

        void treeRotateCheck() {
            final int s1 = treeLeft.size();
            final int s2 = treeRight.size();
            if (s1 - s2 > 1)
                rotateTreeRight();
            if (s2 - s1 > 1)
                rotateTreeLeft();
        }

        void rotateCheck() {
            final int s1 = left != null ? left.size() : 0;
            final int s2 = right != null ? right.size() : 0;
            if (s1 - s2 > 1)
                rotateRight();
            if (s2 - s1 > 1)
                rotateLeft();
        }

        long pollFirst() {
            nodeSize--;
            if (treeLeft.size() == 0 && left == null) {
                final long ans = value;
                if (right == null && treeRight.size() == 0)
                    polling = true;
                else {
                    if (right == null)
                        value = treeRight.pollFirst();
                    else if (treeRight.size() == 0) {
                        value = right.pollFirst();
                        if (polling) {
                            right = null;
                            polling = false;
                        }
                    } else {
                        final long v1 = right.peekFirst();
                        final long v2 = treeRight.peekFirst();
                        if (v1 < v2) {
                            value = right.pollFirst();
                            if (polling) {
                                right = null;
                                polling = false;
                            }
                            rotateCheck();
                        } else {
                            value = treeRight.pollFirst();
                            treeRotateCheck();
                        }
                    }
                }
                return ans;
            }
            if (treeLeft.size() == 0) {
                final long v = left.pollFirst();
                if (polling) {
                    left = null;
                    polling = false;
                }
                rotateCheck();
                return v;
            } else if (left == null) {
                final long v = treeLeft.pollFirst();
                treeRotateCheck();
                return v;
            }
            final long v1 = left.peekFirst();
            final long v2 = treeLeft.peekFirst();
            final long v;
            if (v1 < v2) {
                v = left.pollFirst();
                if (polling) {
                    left = null;
                    polling = false;
                }
                rotateCheck();
            } else {
                v = treeLeft.pollFirst();
                treeRotateCheck();
            }
            return v;
        }

        long pollLast() {
            nodeSize--;
            if (treeRight.size() == 0 && right == null) {
                final long ans = value;
                if (left == null && treeLeft.size() == 0)
                    polling = true;
                else {
                    if (left == null)
                        value = treeLeft.pollLast();
                    else if (treeLeft.size() == 0) {
                        value = left.pollLast();
                        if (polling) {
                            left = null;
                            polling = false;
                        }
                    } else {
                        final long v1 = left.peekLast();
                        final long v2 = treeLeft.peekLast();
                        if (v1 > v2) {
                            value = left.pollLast();
                            if (polling) {
                                left = null;
                                polling = false;
                            }
                            rotateCheck();
                        } else {
                            value = treeLeft.pollLast();
                            treeRotateCheck();
                        }
                    }
                }
                return ans;
            }
            if (treeRight.size() == 0) {
                final long v = right.pollLast();
                if (polling) {
                    right = null;
                    polling = false;
                }
                rotateCheck();
                return v;
            } else if (right == null) {
                final long v = treeRight.pollLast();
                treeRotateCheck();
                return v;
            }
            final long v1 = right.peekLast();
            final long v2 = treeRight.peekLast();
            final long v;
            if (v1 > v2) {
                v = right.pollLast();
                if (polling) {
                    right = null;
                    polling = false;
                }
                rotateCheck();
            } else {
                v = treeRight.pollLast();
                treeRotateCheck();
            }
            return v;
        }

        long peekFirst() {
            if (treeLeft.size() == 0 && left == null) {
                return value;
            }
            if (treeLeft.size() == 0) {
                return left.peekFirst();
            }
            if (left == null) {
                return treeLeft.peekFirst();
            }
            final long v1 = left.peekFirst();
            final long v2 = treeLeft.peekFirst();
            return Math.min(v1, v2);
        }

        long peekLast() {
            if (treeRight.size() == 0 && right == null) {
                return value;
            }
            if (treeRight.size() == 0) {
                return right.peekLast();
            } else if (right == null) {
                return treeRight.peekLast();
            }
            final long v1 = right.peekLast();
            final long v2 = treeRight.peekLast();
            return Math.max(v1, v2);
        }

        int size() {
            return nodeSize;
        }

        public String toString() {
            StringBuilder ans = new StringBuilder();
            ans.append("[");
            ans.append(treeLeft);
            ans.append(",");
            ans.append(left);
            ans.append(",*");
            ans.append(value);
            ans.append("*,");
            ans.append(right);
            ans.append(",");
            ans.append(treeRight);
            ans.append("]");
            return ans.toString();
        }
    }

    public ViralTreeSet() {
        size = 0;
        polling = false;
    }

    public boolean add( long x ) {
        if (root != null) {
            boolean flag = root.add(x);
            if (flag)
                size++;
            return flag;
        }
        root = new Node(x);
        size++;
        return true;
    }

    public boolean contains( long x ) {
        if (root != null)
            return root.contains(x);
        return false;
    }

    public long pollFirst() {
        if (root == null)
            throw new NullPointerException("this tree is empty");
        size--;
        final long v = root.pollFirst();
        if (polling) {
            if (size > 0) {
                System.out.println("root=" + root);
                throw new NullPointerException("polling trigger is broken.");
            }
            root = null;
            polling = false;
        }
        return v;
    }

    public long pollLast() {
        if (root == null)
            throw new NullPointerException("this tree is empty");
        size--;
        final long v = root.pollLast();
        if (polling) {
            if (size > 0) {
                System.out.println("root=" + root);
                throw new NullPointerException("polling trigger is broken.");
            }
            root = null;
            polling = false;
        }
        return v;
    }

    public long peekFirst() {
        if (root == null)
            throw new NullPointerException("this tree is empty");
        return root.peekFirst();
    }

    public long peekLast() {
        if (root == null)
            throw new NullPointerException("this tree is empty");
        return root.peekLast();
    }

    public int size() {
        return size;
    }

    public String toString() {
        if (root == null)
            return "[]";
        return root.toString();
    }

    public static void main( String[] args ) {
        ViralTreeSet set = new ViralTreeSet();
        int size = (int) 2e5;
        final long time = System.nanoTime();
        for ( int i = 1; i <= size; i++ ) {
            set.add(i);
            if (set.size() != i) {
                System.out.println("size is broken:" + i + "->" + set.size());
                return;
            }
        }
        boolean allContain = true;
        for ( int i = 1; i <= size; i++ ) {
            allContain &= set.contains(i);
        }
        System.out.println((System.nanoTime() - time) / 1e6 + "ms");
        System.out.println("allContain:" + allContain);
        //System.out.println(set);
    }
}

class MTRandom extends java.util.Random {
    private final static int[] MAGIC = {0, 0x9908b0df};
    private transient int[] mt;
    private transient int mti;

    public MTRandom() {
        this.setSeed((int) System.currentTimeMillis());
    }

    private void setSeed( int seed ) {
        if (mt == null) mt = new int[624];
        mt[0] = seed;
        for ( mti = 1; mti < 624; mti++ ) mt[mti] = (1812433253 * (mt[mti - 1] ^ (mt[mti - 1] >>> 30)) + mti);
    }

    protected final int next( int bits ) {
        int y = 0, kk;
        if (mti >= 624) {
            for ( kk = 0; kk < 227; kk++ ) {
                y = (mt[kk] & 0x80000000) | (mt[kk + 1] & 0x7fffffff);
                mt[kk] = mt[kk + 397] ^ (y >>> 1) ^ MAGIC[y & 1];
            }
            for ( ; kk < 623; kk++ ) {
                y = (mt[kk] & 0x80000000) | (mt[kk + 1] & 0x7fffffff);
                mt[kk] = mt[kk - 227] ^ (y >>> 1) ^ MAGIC[y & 1];
            }
            y = (mt[623] & 0x80000000) | (mt[0] & 0x7fffffff);
            mt[623] = mt[396] ^ (y >>> 1) ^ MAGIC[y & 1];
            mti = 0;
        }
        y = mt[mti++] ^ (y >>> 11) ^ ((y << 7) & 0x9d2c5680) ^ ((y << 15) & 1664525) ^ (y >>> 18);
        return (y >>> (32 - bits));
    }
}
