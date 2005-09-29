package org.codehaus.mojo.natives.plugin;

/*
 * The MIT License
 *
 * Copyright (c) 2004, The Codehaus
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
*/

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.codehaus.mojo.natives.NativeBuildException;
import org.codehaus.mojo.natives.compiler.MessageCompiler;
import org.codehaus.mojo.natives.compiler.MessageCompilerConfiguration;
import org.codehaus.mojo.natives.manager.MessageCompilerManager;
import org.codehaus.mojo.natives.manager.NoSuchNativeProviderException;

import java.io.File;

/**
 * @goal compile-message
 * @description compile all source into native object files
 * @phase generate-sources
 * @author <a href="dantran@gmail.com">Dan T. Tran</a>
 * @version $Id:$
 */

public class NativeMessageCompileMojo
    extends AbstractMojo
{

    /**
     * @parameter default-value="msvc"
     * @required
     * @description Compiler Provider Type
     */
    private String provider;

    /**
     * @parameter default-value=""
     * @description Provider Installation Directory
     */
    private File providerHome;
    
    /**
     * @description Compiler options
     * @parameter 
     */
    private String [] options;

    /**
     * @parameter expression="${project}"
     * @required
     * @readonly
     */
    protected MavenProject project;
    
    /**
     * @description where to place the genered include files
     * @parameter expression="${project.build.directory}/native"
     * @required
     */
    protected File outputDirectory;

    /**
     * @parameter 
     */
    protected File debugOutputDirectory;

    /**
     * @parameter expression="${basedir}
     * @required
     * @readonly
     */
    protected File basedir;
    
    /**
     * @parameter 
     * @required
     */
    protected File [] messageFiles;
    


    /**
     * @parameter expression="${component.org.codehaus.mojo.natives.manager.MessageCompilerManager}"
     * @required
     */
    private MessageCompilerManager manager;

    
    public void execute()
        throws MojoExecutionException
    {
        
        if ( ! this.outputDirectory.exists() )
        {
            this.outputDirectory.mkdirs();
        }
        
    	MessageCompiler compiler;
        
    	try 
    	{
    	    compiler = this.manager.getMessageCompiler( this.provider );
    	}
    	catch ( NoSuchNativeProviderException pe )
    	{
    		throw new MojoExecutionException( pe.getMessage() );
    	}
    	
    	MessageCompilerConfiguration config = new MessageCompilerConfiguration();
        
    	config.setProviderHome( this.providerHome );
    	config.setBaseDir( this.basedir );
    	config.setOutputDirectory ( this.outputDirectory );
        config.setDebugOutputDirectory ( this.debugOutputDirectory );
        config.setOptions( AbstractNativeMojo.trimParams( this.options ) );
        
    	try 
    	{
    		compiler.compile( config, this.messageFiles );
    	}
    	catch ( NativeBuildException e ) 
    	{
    		throw new MojoExecutionException ( e.getMessage(), e );
    	}
        
        this.project.addCompileSourceRoot( this.outputDirectory.getAbsolutePath() );

    }

}