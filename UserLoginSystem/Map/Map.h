#ifndef DATA_STRUCTURES_MAP_H
#define DATA_STRUCTURES_MAP_H

#include <iostream>

template<typename T, typename U>
class Pair {
private:
    T first;
    U second;

public:
    explicit Pair(T k, U v) : first(k), second(v) {}

    Pair(const Pair<T, U> &ori) : first(ori.first), second(ori.second) {}

    T getFirst() const {
        return first;
    }

    void setFirst(T k) {
        first = k;
    }

    U getSecond() const {
        return second;
    }

    U &getSecond() {
        return second;
    }

    void setSecond(U v) {
        second = v;
    }
};

template<typename T, typename U>
class Map {
private:
    class Node {
    private:
        Node *left;
        Node *right;
        Node *parent;
        bool isBlack;
        Pair<T, U> data;
    public:
        explicit Node(Pair<T, U> data) : data(data), left(nullptr), right(nullptr), parent(nullptr), isBlack(false) {}

        explicit Node(Pair<T, U> data, Node *father) : data(data), left(nullptr), right(nullptr), parent(father),
                                                       isBlack(false) {}

        Node(const Node &ori) : left(ori.left), right(ori.right), data(ori.data) {}

        Node *getLeft() const {
            return left;
        }

        void setLeft(Node *l) {
            Node::left = l;
        }

        bool hasLeft() const {
            return left != nullptr;
        }

        Node *getRight() const {
            return right;
        }

        void setRight(Node *r) {
            Node::right = r;
        }

        bool hasRight() const {
            return right != nullptr;
        }

        Pair<T, U> &getData() {
            return data;
        }

        Pair<T, U> getData() const {
            return data;
        }

        void setData(const Pair<T, U> &d) {
            Node::data = d;
        }

        Node *getParent() const {
            return parent;
        }

        void setParent(Node *f) {
            Node::parent = f;
        }

        bool hasParent() const {
            return parent != nullptr;
        }

        const bool getIsBlack() {
            return isBlack;
        }

        void setIsBlack(bool black) {
            isBlack = black;
        }
    };

public:
    class ConstIterator {
    private:
        const Node *ptr;

    public:
        explicit ConstIterator(Node *ptr) : ptr(ptr) {}

        ConstIterator(const ConstIterator &ori) : ptr(ori.ptr) {}

        ConstIterator &operator=(const ConstIterator &ori) {
            if (&ori == this) {
                return (*this);
            }
            ptr = ori.ptr;
            return (*this);
        }

        Node *getPtr() const {
            return ptr;
        }

        ConstIterator &operator=(const Node *p) {
            ptr = p;
            return (*this);
        }

        explicit operator Node *() {
            return ptr;
        }

        const Pair<T, U> operator*() {
            return ptr->getData();
        }

        const Pair<T, U> *operator->() {
            return &(ptr->getData());
        }

        operator bool() {
            return ptr != nullptr;
        }

        constexpr ConstIterator operator++(int) {
            ConstIterator tmp(*this);
            ++(*this);
            return tmp;
        }

        ConstIterator &operator++() {
            ptr = Map::treeNext(ptr);
            return (*this);
        }

        constexpr ConstIterator operator--(int) {
            ConstIterator tmp(*this);
            --(*this);
            return tmp;
        }

        ConstIterator &operator--() {
            ptr = Map::treePrev(ptr);
            return (*this);
        }

        bool operator==(const ConstIterator &rhs) {
            return ptr->getData().getFirst() == rhs.ptr->getData().getFirst();
        }

        bool operator!=(const ConstIterator &rhs) {
            return ptr->getData().getFirst() != rhs.ptr->getData().getFirst();
        }

        bool operator<(const ConstIterator &rhs) {
            return ptr->getData().getFirst() < rhs.ptr->getData().getFirst();
        }

        bool operator<=(const ConstIterator &rhs) {
            return ptr->getData().getFirst() <= rhs.ptr->getData().getFirst();
        }

        bool operator>(const ConstIterator &rhs) {
            return ptr->getData().getFirst() > rhs.ptr->getData().getFirst();
        }

        bool operator>=(const ConstIterator &rhs) {
            return ptr->getData().getFirst() >= rhs.ptr->getData().getFirst();
        }
    };

    class Iterator {
    private:
        Node *ptr;

    public:
        explicit Iterator(Node *ptr) : ptr(ptr) {}

        Iterator(const Iterator &ori) : ptr(ori.ptr) {}

        Iterator &operator=(const Iterator &ori) {
            if (&ori == this) {
                return (*this);
            }
            ptr = ori.ptr;
            return (*this);
        }

        Iterator &operator=(Node *p) {
            ptr = p;
            return (*this);
        }

        Iterator &operator=(const ConstIterator &it) {
            ptr = it.getPtr();
            return (*this);
        }

        Node *getPtr() const {
            return ptr;
        }

        Pair<T, U> &operator*() {
            return ptr->getData();
        }

        Pair<T, U> *operator->() {
            return &(ptr->getData());
        }

        operator bool() {
            return ptr != nullptr;
        }

        constexpr Iterator operator++(int) {
            Iterator tmp(*this);
            ++(*this);
            return tmp;
        }

        Iterator &operator++() {
            ptr = Map::treeNext(ptr);
            return (*this);
        }

        constexpr Iterator operator--(int) {
            Iterator tmp(*this);
            --(*this);
            return tmp;
        }

        Iterator &operator--() {
            ptr = Map::treePrev(ptr);
            return (*this);
        }

        bool operator==(const Iterator &rhs) {
            return rhs.getPtr() != nullptr && ptr->getData().getFirst() == rhs.ptr->getData().getFirst();
        }

        bool operator!=(const Iterator &rhs) {
            return rhs.getPtr() != ptr && (rhs.getPtr() == nullptr || ptr == nullptr || ptr->getData().getFirst() != rhs.ptr->getData().getFirst());
        }

        bool operator<(const Iterator &rhs) {
            return ptr->getData().getFirst() < rhs.ptr->getData().getFirst();
        }

        bool operator<=(const Iterator &rhs) {
            return ptr->getData().getFirst() <= rhs.ptr->getData().getFirst();
        }

        bool operator>(const Iterator &rhs) {
            return ptr->getData().getFirst() > rhs.ptr->getData().getFirst();
        }

        bool operator>=(const Iterator &rhs) {
            return ptr->getData().getFirst() >= rhs.ptr->getData().getFirst();
        }
    };

private:
    // 判断x是否为父节点的左子节点，若是则返回1，否则返回0，x为根节点则返回-1
    static int treeIsLeftChild(Node *x) {
        if (!x->hasParent()) {
            return -1;
        }
        return x == x->getParent()->getLeft();
    }

    // 返回以r为根的子树中最小的元素
    static Node *treeMin(Node *r) {
        if (!r) {
            return nullptr;
        }
        while (r->hasLeft()) {
            r = r->getLeft();
        }
        return r;
    }

    // 返回以r为根的子树中最大的元素
    static Node *treeMax(Node *r) {
        if (!r) {
            return nullptr;
        }
        while (r->hasRight()) {
            r = r->getRight();
        }
        return r;
    }

    // 返回x的下一个节点
    static Node *treeNext(Node *x) {
        if (x->hasRight()) {
            return treeMin(x->getRight());
        }
        while (treeIsLeftChild(x) == 0) {
            x = x->getParent();
        }
        if (treeIsLeftChild(x) == -1) {
            return nullptr;
        }
        return x->getParent();
    }

    // 返回x的上一个节点
    static Node *treePrev(Node *x) {
        if (x->hasLeft()) {
            return treeMax(x->getLeft());
        }
        while (treeIsLeftChild(x) == 1) {
            x = x->getParent();
        }
        if (treeIsLeftChild(x) == -1) {
            return nullptr;
        }
        return x->getParent();
    }

    // 返回以x为根的子树下键为key的节点，若没有则返回key该插入处的根节点
    static Node *treeFind(Node *x, T key) {
        Node *ptr = x;
        while (ptr->getData().getFirst() != key) {
            if (ptr->getData().getFirst() > key && ptr->hasLeft()) {
                ptr = ptr->getLeft();
            } else if (ptr->getData().getFirst() < key && ptr->hasRight()) {
                ptr = ptr->getRight();
            } else {
                break;
            }
        }
        return ptr;
    }

    // 左转以x为根的子树
    static void treeLeftRotate(Node *x) {
        Node *y = x->getRight();
        x->setRight(y->getLeft());
        if (x->hasRight()) {
            x->getRight()->setParent(x);
        }
        y->setParent(x->getParent());
        if (treeIsLeftChild(x) == 1) {
            x->getParent()->setLeft(y);
        } else if (x->hasParent()) {
            x->getParent()->setRight(y);
        }
        y->setLeft(x);
        x->setParent(y);
    }

    // 右转以x为根的子树
    static void treeRightRotate(Node *x) {
        Node *y = x->getLeft();
        x->setLeft(y->getRight());
        if (x->hasLeft()) {
            x->getLeft()->setParent(x);
        }
        y->setParent(x->getParent());
        if (treeIsLeftChild(x) == 1) {
            x->getParent()->setLeft(y);
        } else if (x->hasParent()) {
            x->getParent()->setRight(y);
        }
        y->setRight(x);
        x->setParent(y);
    }

    // 插入后对根为r，插入节点为x的树进行自平衡
    void treeBalanceAfterInsert(Node *x) {
        x->setIsBlack(x == root);
        while (x != root && !x->getParent()->getIsBlack()) {
            // 此时x的父节点一定不为根节点
            if (treeIsLeftChild(x->getParent()) == 1) {
                // 父节点是祖父节点的左子节点时
                Node *y = x->getParent()->getParent()->getRight();
                if (y != nullptr && !y->getIsBlack()) {
                    // 祖父节点的右子节点为红色
                    // 父节点与叔节点改为黑色，祖父节点改为红色（除根节点外）
                    // 并将指针更新为祖父节点后再次处理红节点连续问题
                    x = x->getParent();
                    x->setIsBlack(true);
                    x = x->getParent();
                    x->setIsBlack(x == root);
                    y->setIsBlack(true);
                } else {
                    // 祖父节点不存在右子节点或右子节点为黑色
                    if (treeIsLeftChild(x) == 0) {
                        // x为父节点的右子节点时将以父节点为根的子树进行左旋转
                        // 将指针更新为旋转前的父节点，旋转后的左子节点
                        // 这一步的作用是将<号变成/号
                        // 使其转换x为父节点的左子节点的情况
                        x = x->getParent();
                        treeLeftRotate(x);
                    }
                    // 将父节点改为黑色，祖父节点改为红色
                    // 将以祖父节点为根的子树进行右旋转
                    // 这一步是将/号变成^号，完成平衡
                    x = x->getParent();
                    x->setIsBlack(true);
                    x = x->getParent();
                    x->setIsBlack(false);
                    treeRightRotate(x);
                    break;
                }
            } else {
                // 父节点是祖父节点的右子节点时
                Node *y = x->getParent()->getParent()->getLeft();
                if (y != nullptr && !y->getIsBlack()) {
                    // 祖父节点的左子节点为红色
                    // 父节点与叔节点改为黑色，祖父节点改为红色（除根节点外）
                    // 并将指针更新为祖父节点后再次处理红节点连续问题
                    x = x->getParent();
                    x->setIsBlack(true);
                    x = x->getParent();
                    x->setIsBlack(x == root);
                    y->setIsBlack(true);
                } else {
                    // 祖父节点不存在左子节点或左子节点为黑色
                    if (treeIsLeftChild(x) == 1) {
                        // x为父节点的左子节点时将以父节点为根的子树进行右旋转
                        // 将指针更新为旋转前的父节点，旋转后的左子节点
                        // 这一步的作用是将>号变成\号
                        // 使其转换x为父节点的左子节点的情况
                        x = x->getParent();
                        treeRightRotate(x);
                    }
                    // 将父节点改为黑色，祖父节点改为红色
                    // 将以祖父节点为根的子树进行左旋转
                    // 这一步是将\号变成^号，完成平衡
                    x = x->getParent();
                    x->setIsBlack(true);
                    x = x->getParent();
                    x->setIsBlack(false);
                    treeLeftRotate(x);
                    break;
                }
            }
        }
    }

    void treeRemove(Node *z) {
        // z指向需要被删除的节点
        // 因为一个节点同时拥有两个子节点时直接删除会导致两个子节点需要争夺一个位置
        // 所以无法直接删除，需要寻找下一个节点（必定不可能同时拥有两个子节点）来填充原位置

        // y指向需要被操作的节点，若z不同时拥有子节点则y等于z，否则y等于z的下一个节点
        Node *y = (z->hasLeft() && z->hasRight() ? treeNext(z) : z);
        // x指向y的一个子节点（可能为空），用于填补y被删除后的位置
        Node *x = y->hasLeft() ? y->getLeft() : y->getRight();
        // w指向x的叔节点（可能为空），即删除操作后x的兄弟节点
        Node *w = nullptr;
        // 将x与y的父节点连接，并且寻找w
        if (x != nullptr) {
            x->setParent(y->getParent());
        }
        if (treeIsLeftChild(y) == -1) {
            root = x; // 此时不存在w
        } else if (treeIsLeftChild(y) == 1) {
            y->getParent()->setLeft(x);
            w = y->getParent()->getRight();
        } else {
            y->getParent()->setRight(x);
            w = y->getParent()->getLeft();
        }
        // 记录被删除的节点y的颜色，若是黑色则需要处理复杂情况
        bool removedBlack = y->getIsBlack();
        // 如果需要其他节点来进行填充，就让y节点完全取代z的位置，y原本的子节点已用x记录
        // y还需要拷贝z的颜色，这些操作都不会影响x与w
        if (y != z) {
            // z的左子节点必不为空，右节点可能等于x且为空（即原本y是z的右子节点）
            y->setParent(z->getParent());
            if (treeIsLeftChild(z) == 1) {
                y->getParent()->setLeft(y);
            } else if (treeIsLeftChild(z) == 0) {
                y->getParent()->setRight(y);
            }
            y->setLeft(z->getLeft());
            y->getLeft()->setParent(y);
            y->setRight(z->getRight());
            if (y->hasRight()) {
                y->getRight()->setParent(y);
            }
            y->setIsBlack(z->getIsBlack());
            if (root == z) {
                root = y;
            }
        }
        // 删除一个红节点或删除根节点无需进行再平衡
        if (removedBlack && root != nullptr) {
            // x要么是无子节点的红节点，要么为空的黑节点
            // x无论原本是什么颜色，在操作过后一定是黑色的，因为它需要填补y的位置
            if (x != nullptr) {
                // x原本是红色的话就只需要将其染黑即可，因为这样不会影响删除后x与w子树的黑高
                // 如果x是根节点的话不用考虑双黑节点，直接确保染黑即可
                x->setIsBlack(true);
            } else {
                // x是空节点的话它就需要有两倍的黑高才能使x与w子树的黑高一致，所以称其为双黑节点
                // 此时w不可能为空，w子树至少要有两个单位的黑高
                // 也即为非空黑节点或有黑色子节点的红节点
                while (true) {
                    if (treeIsLeftChild(w) == 0) {
                        // x为左子节点
                        if (!w->getIsBlack()) {
                            // w为红色节点，需要将w染黑，w的父节点染红
                            // 然后将以父节点为根的子树左旋
                            // 更新w为旋转后x的兄弟节点
                            w->setIsBlack(true);
                            w->getParent()->setIsBlack(false);
                            treeLeftRotate(w->getParent());
                            if (root == w->getLeft()) {
                                // 如果旋转前父节点为根节点，则将根节点的转移给w
                                root = w;
                            }
                            w = w->getLeft()->getRight();
                        }
                        // 此时w必为黑节点，可能有子节点
                        if ((!w->hasLeft() || w->getLeft()->getIsBlack()) &&
                            (!w->hasRight() || w->getRight()->getIsBlack())) {
                            // w没有子节点或子节点均为黑节点
                            // 此时需要递归处理，将x的兄弟节点染红并将x更新为父节点
                            // w更新为父节点的兄弟节点
                            // 直到x为红节点，将其染黑；或是w不满足要求，进行其他操作
                            w->setIsBlack(false);
                            x = w->getParent();
                            if (x == root || !x->getIsBlack()) {
                                x->setIsBlack(true);
                                break;
                            }
                            w = treeIsLeftChild(x) ?
                                x->getParent()->getRight() :
                                x->getParent()->getLeft();
                            // 递归
                        } else {
                            // w至少有一个红子节点
                            if (!w->hasRight() || w->getRight()->getIsBlack()) {
                                // w没有右红子节点，即RL情形
                                // 交换w与其左子节点的颜色后将以w为根节点的子树右旋
                                // 更新w为旋转后x的兄弟节点，此时转换为RR情形
                                w->getLeft()->setIsBlack(true);
                                w->setIsBlack(false);
                                treeRightRotate(w);
                                w = w->getParent();
                            }
                            // w有右红子节点，左子节点可有可无，即RR情形
                            // 将w染成父节点的颜色，w的右子节点与父节点染黑
                            // 将以w父节点为根的子树左旋
                            w->setIsBlack(w->getParent()->getIsBlack());
                            w->getParent()->setIsBlack(true);
                            w->getRight()->setIsBlack(true);
                            treeLeftRotate(w->getParent());
                            break;
                        }
                    } else {
                        // x为右子节点
                        if (!w->getIsBlack()) {
                            // w为红色节点，需要将w染黑，w的父节点染红
                            // 然后将以父节点为根的子树右旋
                            // 更新w为旋转后x的兄弟节点
                            w->setIsBlack(true);
                            w->getParent()->setIsBlack(false);
                            treeRightRotate(w->getParent());
                            if (root == w->getRight()) {
                                // 如果旋转前父节点为根节点，则将根节点的转移给w
                                root = w;
                            }
                            w = w->getRight()->getLeft();
                        }
                        // 此时w必为黑节点，可能有子节点
                        if ((!w->hasLeft() || w->getLeft()->getIsBlack()) &&
                            (!w->hasRight() || w->getRight()->getIsBlack())) {
                            // w没有子节点或子节点均为黑节点
                            // 此时需要递归处理，将x的兄弟节点染红并将x更新为父节点
                            // w更新为父节点的兄弟节点
                            // 直到x为红节点，将其染黑；或是w不满足要求，进行其他操作
                            w->setIsBlack(false);
                            x = w->getParent();
                            if (x == root || !x->getIsBlack()) {
                                x->setIsBlack(true);
                                break;
                            }
                            w = treeIsLeftChild(x) ?
                                x->getParent()->getRight() :
                                x->getParent()->getLeft();
                            // 递归
                        } else {
                            // w至少有一个红子节点
                            if (!w->hasLeft() || w->getLeft()->getIsBlack()) {
                                // w没有左红子节点，即LR情形
                                // 交换w与其右子节点的颜色后将以w为根节点的子树右旋
                                // 更新w为旋转后x的兄弟节点，此时转换为RR情形
                                w->getRight()->setIsBlack(true);
                                w->setIsBlack(false);
                                treeLeftRotate(w);
                                w = w->getParent();
                            }
                            // w有左红子节点，右子节点可有可无，即LL情形
                            // 将w染成父节点的颜色，w的左子节点与父节点染黑
                            // 将以w父节点为根的子树右旋
                            w->setIsBlack(w->getParent()->getIsBlack());
                            w->getParent()->setIsBlack(true);
                            w->getLeft()->setIsBlack(true);
                            treeRightRotate(w->getParent());
                            break;
                        }
                    }
                }
            }

        }
    }

    unsigned int distance(Iterator, Iterator);

    Node *root;

public:
    Map() : root(nullptr) {}

    Map(const Map<T, U> &ori) : root(ori.root) {}

    Map &operator=(const Map<T, U> &);

    ~Map();

    void clear(Node *);

    Iterator begin();

    ConstIterator cBegin();

    Iterator end();

    ConstIterator cEnd();

    bool empty();

    T &at(const T &);

    const T &at(const T &) const;

    unsigned int size();

    unsigned int count(const T &);

    Iterator find(const T &);

    ConstIterator find(const T &) const;

    bool contains(const T &) const;

    Iterator upperBound(const T &);

    ConstIterator upperBound(const T &) const;

    Iterator lowerBound(const T &);

    ConstIterator lowerBound(const T &) const;

    Iterator removeNodePoint(Node *);

    Iterator erase(Iterator);

    Iterator erase(ConstIterator);

    Iterator erase(Iterator, Iterator);

    Iterator erase(ConstIterator, ConstIterator);

    Pair<Iterator, bool> insert(const Pair<T, U> &);

    Pair<Iterator, bool> insert(Pair<T, U> &&);

    Pair<Iterator, bool> insertOrAssign(const T &, U &&);

    Pair<Iterator, bool> insertOrAssign(T &&, U &&);
};

template<typename T, typename U>
unsigned int Map<T, U>::distance(Iterator first, Iterator last) {
    unsigned int sum = 0;
    for (Iterator it = first; it != last; ++it, ++sum);
    return sum;
}

template<typename T, typename U>
Map<T, U> &Map<T, U>::operator=(const Map<T, U> &ori) {
    if (&ori == this) {
        return (*this);
    }
    clear(root);
    root = ori.root;
    return (*this);
}

template<typename T, typename U>
Map<T, U>::~Map() {
    clear(root);
}

template<typename T, typename U>
void Map<T, U>::clear(Node *r) {
    if (!r) {
        return;
    }
    Node *left = r->getLeft();
    Node *right = r->getRight();
    delete r;
    r = nullptr;
    if (left) {
        clear(left);
    }
    if (right) {
        clear(right);
    }
}

template<typename T, typename U>
typename Map<T, U>::Iterator Map<T, U>::begin() {
    return Iterator(treeMin(root));
}

template<typename T, typename U>
typename Map<T, U>::ConstIterator Map<T, U>::cBegin() {
    return ConstIterator(treeMin(root));
}

template<typename T, typename U>
typename Map<T, U>::Iterator Map<T, U>::end() {
    return Iterator(nullptr);
}

template<typename T, typename U>
typename Map<T, U>::ConstIterator Map<T, U>::cEnd() {
    return ConstIterator(nullptr);
}

template<typename T, typename U>
T &Map<T, U>::at(const T &key) {
    Iterator it(treeFind(root, key));
    if (it->getFirst() == key) {
        return (*it).getSecond();
    }
    throw std::runtime_error("OUT_OF_RANGE");
}

template<typename T, typename U>
const T &Map<T, U>::at(const T &key) const {
    ConstIterator it(treeFind(root, key));
    if (it->getFirst() == key) {
        return *it;
    }
    throw std::runtime_error("OUT_OF_RANGE");
}

template<typename T, typename U>
unsigned int Map<T, U>::size() {
    return distance(begin(), end());
}

template<typename T, typename U>
bool Map<T, U>::empty() {
    return begin() == end();
}

template<typename T, typename U>
unsigned int Map<T, U>::count(const T &key) {
    return (contains(key) ? 1 : 0);
}

template<typename T, typename U>
typename Map<T, U>::Iterator Map<T, U>::find(const T &key) {
    if (!root) {
        return end();
    }
    Iterator it(treeFind(root, key));
    return (it->getFirst() == key ? it : end());
}

template<typename T, typename U>
typename Map<T, U>::ConstIterator Map<T, U>::find(const T &key) const {
    if (!root) {
        return end();
    }
    ConstIterator it(treeFind(root, key));
    return (it->getFirst() == key ? it : cEnd());
}

template<typename T, typename U>
bool Map<T, U>::contains(const T &key) const {
    if (!root) {
        return false;
    }
    ConstIterator it(treeFind(root, key));
    return it->getFirst() == key;
}

template<typename T, typename U>
typename Map<T, U>::Iterator Map<T, U>::upperBound(const T &key) {
    Iterator it(treeFind(root, key));
    return (it->getFirst() > key ? it : ++it);
}

template<typename T, typename U>
typename Map<T, U>::ConstIterator Map<T, U>::upperBound(const T &key) const {
    ConstIterator it(treeFind(root, key));
    return (it->getFirst() > key ? it : ++it);
}

template<typename T, typename U>
typename Map<T, U>::Iterator Map<T, U>::lowerBound(const T &key) {
    Iterator it(treeFind(root, key));
    return (it->getFirst() > key ? --it : it);
}

template<typename T, typename U>
typename Map<T, U>::ConstIterator Map<T, U>::lowerBound(const T &key) const {
    ConstIterator it(treeFind(root, key));
    return (it->getFirst() > key ? --it : it);
}

template<typename T, typename U>
typename Map<T, U>::Iterator Map<T, U>::erase(Map::Iterator pos) {
    return removeNodePoint(pos.getPtr());
}

template<typename T, typename U>
typename Map<T, U>::Iterator Map<T, U>::erase(Map::ConstIterator pos) {
    return removeNodePoint(pos.getPtr());
}

template<typename T, typename U>
typename Map<T, U>::Iterator Map<T, U>::erase(Map::Iterator first, Map::Iterator last) {
    while (first != last) {
        first = erase(first);
    }
    return last;
}

template<typename T, typename U>
typename Map<T, U>::Iterator Map<T, U>::erase(Map::ConstIterator first, Map::ConstIterator last) {
    while (first != last) {
        first = erase(first);
    }
    return last;
}

template<typename T, typename U>
Pair<typename Map<T, U>::Iterator, bool> Map<T, U>::insert(const Pair<T, U> &pair) {
    Node *node = new Node(pair);
    if (!root) {
        root = node;
    } else {
        Node *pos = treeFind(root, pair.getFirst());
        if (pos->getData().getFirst() == pair.getFirst()) {
            return Pair<typename Map<T, U>::Iterator, bool>(Iterator(pos), false);
        }
        node->setParent(pos);
        if (pos->getData().getFirst() > pair.getFirst()) {
            pos->setLeft(node);
        } else {
            pos->setRight(node);
        }
    }
    treeBalanceAfterInsert(node);
    while (root && root->hasParent()) {
        root = root->getParent();
    }
    return Pair<typename Map<T, U>::Iterator, bool>(Iterator(node), true);
}

template<typename T, typename U>
Pair<typename Map<T, U>::Iterator, bool> Map<T, U>::insert(Pair<T, U> &&pair) {
    Node *node = new Node(pair);
    if (!root) {
        root = node;
    } else {
        Node *pos = treeFind(root, pair.getFirst());
        if (pos->getData().getFirst() == pair.getFirst()) {
            return Pair<typename Map<T, U>::Iterator, bool>(Iterator(pos), false);
        }
        node->setParent(pos);
        if (pos->getData().getFirst() > pair.getFirst()) {
            pos->setLeft(node);
        } else {
            pos->setRight(node);
        }
    }
    treeBalanceAfterInsert(node);
    while (root && root->hasParent()) {
        root = root->getParent();
    }
    return Pair<typename Map<T, U>::Iterator, bool>(Iterator(node), true);
}

template<typename T, typename U>
Pair<typename Map<T, U>::Iterator, bool> Map<T, U>::insertOrAssign(const T &key, U &&value) {
    Node *pos = treeFind(root, key);
    if (pos->getData().getFirst() == key) {
        pos->getData().setSecond(value);
        return Pair<typename Map<T, U>::Iterator, bool>(Iterator(pos));
    } else {
        return insert(Pair<T, U>(key, value));
    }
}

template<typename T, typename U>
Pair<typename Map<T, U>::Iterator, bool> Map<T, U>::insertOrAssign(T &&key, U &&value) {
    Node *pos = treeFind(root, key);
    if (pos->getData().getFirst() == key) {
        pos->getData().setSecond(value);
        return Pair<typename Map<T, U>::Iterator, bool>(Iterator(pos), false);
    } else {
        return insert(Pair<T, U>(key, value));
    }
}

template<typename T, typename U>
typename Map<T, U>::Iterator Map<T, U>::removeNodePoint(Node *ptr) {
    Iterator r(ptr);
    ++r;
    treeRemove(ptr);
    while (root && root->hasParent()) {
        root = root->getParent();
    }
    delete ptr;
    return r;
}


#endif //DATA_STRUCTURES_MAP_H