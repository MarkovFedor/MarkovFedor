package common;

import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class BaseEntity {
    public abstract Long getId();
    public abstract void setId(Long id);
    public abstract String getName();
    public abstract void setName(final String name);
}