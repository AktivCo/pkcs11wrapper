package ru.rutoken.pkcs11wrapper.object.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ru.rutoken.pkcs11wrapper.attribute.IPkcs11AttributeFactory;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11Attribute;

/**
 * Makes template walking through a node tree and collecting attributes.
 */
class TemplateMakerVisitor implements ObjectFactoryNodeVisitor {
    private final List<Pkcs11Attribute> mTemplate = new ArrayList<>();
    private final IPkcs11AttributeFactory mAttributeFactory;

    public TemplateMakerVisitor(IPkcs11AttributeFactory factory) {
        mAttributeFactory = Objects.requireNonNull(factory);
    }

    @Override
    public void visit(ObjectFactoryNode<?> node) {
        ObjectFactoryNode<?> currentNode = node;
        while (null != currentNode) {
            final ObjectFactoryNode<?> parent = currentNode.getParent();
            final Object value = currentNode.getParentAttributeValue();
            if (null == parent || null == value)
                break;
            final Pkcs11Attribute attribute = parent.getAttribute();
            if (null == attribute)
                break;
            mTemplate.add(mAttributeFactory.makeAttribute(attribute.getType(), value));
            currentNode = currentNode.getParent();
        }
    }

    List<Pkcs11Attribute> getTemplate() {
        return mTemplate;
    }
}
