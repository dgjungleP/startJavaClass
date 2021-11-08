package com.jungle.process.annotation;

import com.jungle.process.annotation.Data;
import com.sun.source.tree.Tree;
import com.sun.tools.javac.api.JavacTrees;
import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.code.Type;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.tree.TreeTranslator;
import com.sun.tools.javac.util.*;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.Set;

@SupportedAnnotationTypes("com.jungle.process.annotation.Data")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class DataAnnotationProcessor
        extends AbstractProcessor {
    private JavacTrees javacTrees;
    private TreeMaker treeMaker;
    private Names names;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        Context context = ((JavacProcessingEnvironment) processingEnv).getContext();
        javacTrees = JavacTrees.instance(context);
        treeMaker = TreeMaker.instance(context);
        names = Names.instance(context);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(Data.class);
        for (Element element : elements) {
            JCTree tree = javacTrees.getTree(element);
            tree.accept(new TreeTranslator() {
                @Override
                public void visitClassDef(JCTree.JCClassDecl jcClassDecl) {
                    jcClassDecl.defs.stream()
                            .filter(it -> it.getKind().equals(Tree.Kind.VARIABLE))
                            .map(it -> (JCTree.JCVariableDecl) it)
                            .forEach(it -> {
                                jcClassDecl.defs = jcClassDecl.defs.prepend(genGetterMethod(it));
                                jcClassDecl.defs = jcClassDecl.defs.prepend(genSetterMethod(it));

                            });
                    super.visitClassDef(jcClassDecl);
                }
            });
        }
        return true;
    }

    private JCTree genSetterMethod(JCTree.JCVariableDecl variableDecl) {
        JCTree.JCExpressionStatement statement = treeMaker.Exec(
                treeMaker.Assign(
                        treeMaker.Select(
                                treeMaker.Ident(names.fromString("this")),
                                variableDecl.getName()),
                        treeMaker.Ident(variableDecl.getName())));
        ListBuffer<JCTree.JCStatement> statements = new ListBuffer<JCTree.JCStatement>().append(statement);
        JCTree.JCModifiers modifiers = treeMaker.Modifiers(Flags.PUBLIC);
        JCTree.JCVariableDecl parameter = treeMaker.VarDef(
                treeMaker.Modifiers(Flags.PARAMETER, List.nil()),
                variableDecl.getName(),
                variableDecl.vartype,
                null
        );
        Name getMethodName = setMethodName(variableDecl.getName());
        JCTree.JCExpression returnType = treeMaker.Type(new Type.JCVoidType());
        JCTree.JCBlock block = treeMaker.Block(0, statements.toList());
        List<JCTree.JCTypeParameter> methodGenericParamList = List.nil();
        List<JCTree.JCVariableDecl> parameterList = List.of(parameter);
        List<JCTree.JCExpression> throwCauseList = List.nil();
        return treeMaker.MethodDef(modifiers, getMethodName, returnType, methodGenericParamList, parameterList, throwCauseList, block, null);
    }

    private Name setMethodName(Name name) {
        String nameStr = name.toString();
        String tail = nameStr.substring(1);
        String head = nameStr.substring(0, 1);
        return names.fromString("set" + head.toUpperCase() + tail);
    }

    private JCTree genGetterMethod(JCTree.JCVariableDecl variableDecl) {
        JCTree.JCReturn jcReturn = treeMaker.Return(treeMaker.Select(treeMaker.Ident(names.fromString("this")), variableDecl.getName()));
        ListBuffer<JCTree.JCStatement> statements = new ListBuffer<JCTree.JCStatement>().append(jcReturn);
        JCTree.JCModifiers modifiers = treeMaker.Modifiers(Flags.PUBLIC);
        Name getMethodName = getMethodName(variableDecl.getName());
        JCTree.JCExpression vartype = variableDecl.vartype;
        JCTree.JCBlock block = treeMaker.Block(0, statements.toList());
        List<JCTree.JCTypeParameter> methodGenericParamList = List.nil();
        List<JCTree.JCVariableDecl> parameterList = List.nil();
        List<JCTree.JCExpression> throwCauseList = List.nil();
        return treeMaker.MethodDef(modifiers, getMethodName, vartype, methodGenericParamList, parameterList, throwCauseList, block, null);
    }

    private Name getMethodName(Name name) {
        String nameStr = name.toString();
        String tail = nameStr.substring(1);
        String head = nameStr.substring(0, 1);
        return names.fromString("get" + head.toUpperCase() + tail);
    }
}
