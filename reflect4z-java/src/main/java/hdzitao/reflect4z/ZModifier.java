package hdzitao.reflect4z;

import java.lang.reflect.Modifier;

public class ZModifier {
    private final int modifier;

    public ZModifier(int modifier) {
        this.modifier = modifier;
    }

    public int modifier() {
        return this.modifier;
    }

    /**
     * 判断 abstract 修饰
     *
     * @return true 如果是abstract修饰
     */
    public boolean isAbstract() {
        return Modifier.isAbstract(this.modifier);
    }

    /**
     * public修饰
     *
     * @return true 如果是public修饰
     */
    public boolean isPublic() {
        return Modifier.isPublic(this.modifier);
    }

    /**
     * protected修饰
     *
     * @return true 如果是protected修饰
     */
    public boolean isProtected() {
        return Modifier.isProtected(this.modifier);
    }

    /**
     * 默认修饰
     *
     * @return true 如果是默认修饰
     */
    public boolean isPackaged() {
        return (this.modifier & (Modifier.PUBLIC | Modifier.PROTECTED | Modifier.PRIVATE)) == 0;
    }

    /**
     * private修饰
     *
     * @return true 如果是private修饰
     */
    public boolean isPrivate() {
        return Modifier.isPrivate(this.modifier);
    }

    /**
     * final修饰
     *
     * @return true 如果是final修饰
     */
    public boolean isFinal() {
        return Modifier.isFinal(this.modifier);
    }

    /**
     * 判断 native 修饰
     *
     * @return true 如果是native修饰
     */
    public boolean isNative() {
        return Modifier.isNative(this.modifier);
    }

    /**
     * 判断static 修饰
     *
     * @return true 如果是static修饰
     */
    public boolean isStatic() {
        return Modifier.isStatic(this.modifier);
    }

    /**
     * 判断strict 修饰
     *
     * @return true 如果是strict 修饰
     */
    public boolean isStrict() {
        return Modifier.isStrict(this.modifier);
    }

    /**
     * 判断synchronized 修饰
     *
     * @return true 如果是synchronized 修饰
     */
    public boolean isSynchronized() {
        return Modifier.isSynchronized(this.modifier);
    }

    /**
     * 判断transient 修饰
     *
     * @return true 如果是transient 修饰
     */
    public boolean isTransient() {
        return Modifier.isTransient(this.modifier);
    }

    /**
     * 判断volatile 修饰
     *
     * @return true 如果是volatile 修饰
     */
    public boolean isVolatile() {
        return Modifier.isVolatile(this.modifier);
    }
}
