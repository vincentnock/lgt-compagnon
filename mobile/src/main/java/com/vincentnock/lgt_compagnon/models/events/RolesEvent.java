package com.vincentnock.lgt_compagnon.models.events;

import com.vincentnock.lgt_compagnon.models.Role;

import java.util.List;

/**
 * Created by vincent on 26/12/2016.
 */
public class RolesEvent {
    public List<Role> roles;

    public RolesEvent(List<Role> roles) {
        this.roles = roles;
    }
}
