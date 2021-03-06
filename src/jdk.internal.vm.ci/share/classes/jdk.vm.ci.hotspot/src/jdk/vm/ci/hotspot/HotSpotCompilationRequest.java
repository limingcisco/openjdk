/*
 * Copyright (c) 2015, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
package jdk.vm.ci.hotspot;

import jdk.vm.ci.code.CompilationRequest;

/**
 * A compilation request with extra HotSpot specific context such as a compilation identifier and
 * the address of a {@code JVMCIEnv} object that provides native context for a compilation.
 */
public class HotSpotCompilationRequest extends CompilationRequest {
    private final long jvmciEnv;
    private final int id;

    /**
     * Creates a request to compile a method starting at a given BCI and allocates an identifier to
     * the request.
     *
     * @param method the method to be compiled
     * @param entryBCI the bytecode index (BCI) at which to start compiling where -1 denotes the
     *            method's entry point
     * @param jvmciEnv address of a native {@code JVMCIEnv} object or 0L
     */
    public HotSpotCompilationRequest(HotSpotResolvedJavaMethod method, int entryBCI, long jvmciEnv) {
        this(method, entryBCI, jvmciEnv, method.allocateCompileId(entryBCI));
    }

    /**
     * Creates a request to compile a method starting at a given BCI.
     *
     * @param method the method to be compiled
     * @param entryBCI the bytecode index (BCI) at which to start compiling where -1 denotes the
     *            method's entry point
     * @param jvmciEnv address of a native {@code JVMCIEnv} object or 0L
     * @param id an identifier for the request
     */
    public HotSpotCompilationRequest(HotSpotResolvedJavaMethod method, int entryBCI, long jvmciEnv, int id) {
        super(method, entryBCI);
        this.jvmciEnv = jvmciEnv;
        this.id = id;
    }

    @Override
    public HotSpotResolvedJavaMethod getMethod() {
        return (HotSpotResolvedJavaMethod) super.getMethod();
    }

    /**
     * Gets the address of the native {@code JVMCIEnv} object or 0L if no such object exists.
     */
    public long getJvmciEnv() {
        return jvmciEnv;
    }

    /**
     * Gets the VM allocated identifier for this compilation.
     */
    public int getId() {
        return id;
    }

}
