package com.github.nowakprojects.personaleducation.oracletutorial.reflectionapi.classes;

public class Inherited extends Parent {
    public String test;

    public class PublicMemberClass{

    }

    class MemberClass{

    }

    private class PrivateMemberClass{

    }
}

class Parent extends ParentOfParent implements ParentInterface {

}

class ParentOfParent {

    private final EnumFieldInParentOfParent field = EnumFieldInParentOfParent.A;

    protected final ProtectedFieldOfParent protectedField = ProtectedFieldOfParent.B;

    public EnumFieldInParentOfParent getField() {
        return field;
    }
}

interface ParentInterface {

}

enum EnumFieldInParentOfParent {
    A, B, C;
}

enum ProtectedFieldOfParent {
    A, B, C;
}

