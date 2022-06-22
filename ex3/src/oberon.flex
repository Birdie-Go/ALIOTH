import java.io.*;
import exceptions.*;
import java_cup.runtime.*;

%%

%public
%class OberonScanner
%unicode
%ignorecase
%line 
%column
%yylexthrow LexicalException
%type String
%eofval{
	return "_";
%eofval}

%{
	int getLine() {
		return yyline + 1;
	}
	int getColumn() {
		return yycolumn + 1;
	}
%}


ReservedWord = "module"|"begin"|"end"|"const"|"type"|"var"|"procedure"|"record"|"array"|"of"|"while"|"do"|"if"|"then"|"elsif"|"else"|"or"|"div"|"mod" 
Keyword = "integer"|"boolean"|"read"|"write"|"writeln"
Operator = "="|"#"|"<"|"<="|">"|">="|"*"|"div"|"mod"|"+"|"-"|"&"|"or"|"~"|":="|":"|"("|")"|"["|"]"
Punctuation = ";"|","|"."
Comment = "(*"~"*)"
Identifier = [:jletter:]+[:jletterdigit:]*
Integer = 0[0-7]* | [1-9]+[0-9]*
IllegalOctal = 0[0-7]* [8|9|"."]+ [0-9]*
IllegalInteger 	= {Integer}+{Identifier}+
MismatchedComment= "(*" ( [^\*] | "*"+[^\)] )* | ( [^\(] | "("+[^\*] )* "*)"
WhiteSpace 	= " "|\t|\r\n|\r|\n

%%

<YYINITIAL>
{
	{ReservedWord}						{return "ReservedWord";}
	{Keyword}							{return "Keyword";}
	{Operator}							{return "Operator";}
	{Punctuation}						{return "Punctuation";}
	{Comment}							{return "Comment";}
	{Identifier}						{
											if (yylength() > 24)
												throw new IllegalIdentifierLengthException();
											else
												return "Identifier";
										}
	{Integer}							{
											if (yylength() > 12)
												throw new IllegalIntegerRangeException();
											else
												return "Integer";
										}
	{IllegalOctal}						{throw new IllegalOctalException();}
	{IllegalInteger}					{throw new IllegalIntegerException();}
	{MismatchedComment}					{throw new MismatchedCommentException();}
	{WhiteSpace}						{}
	.									{throw new IllegalSymbolException();} 
}