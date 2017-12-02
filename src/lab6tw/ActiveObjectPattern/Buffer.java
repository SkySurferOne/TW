package lab6tw.ActiveObjectPattern;

interface Buffer {
    int put(int number) throws Exception;
    int get(int number) throws Exception;
}
