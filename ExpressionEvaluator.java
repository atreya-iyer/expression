import java.util.Scanner;
/******************************************************************************
 * Name: Atreya Iyer
 * Block: D 
 * Date: 3/5/16
 * <p>
 * Program 9: Expression Evaluator
 * Description: <p>
 * 		While loop to allow user to go again. Generate tree calls getToken 
 * repeatedly until the user enters the end key legally. GenerateTree checks if
 * any user commands are valid and pushes (or pops) onto the stack. A 
 * program that reads in arithmetic expressions in postfix notation, constructs
 * the corresponding expression tree, evaluates the expression tree, and then 
 * prints the result. 
 * 
 * ***************************************************************************/
public class ExpressionEvaluator 
{
	private static final String ERROR = "Error: Invalid entry - ";
	private static final Scanner SCAN = new Scanner(System.in);
	private static final String MENU = "Commands:\nToken types:\n'#' = number"
			+ "\n'u' = unary operator\n'b' = binary operator\n"
			+ "'e' = end\n\nUnary Operators:\n'-' = negative\n'+' = positive\n"
			+ "'q' = square root\n'a' = absolute value\n'l' = natural log\n'e'"
			+ " = e^x\n's' = sin\n'c' = cos\n't' = "
			+ "tan\n\nBinary Operators:\n'+' = add\n'-' = "
			+ "subtract\n'*' = multiply\n'/' = divide\n\n'h' = "
			+ "help menu\n\n";

	/**
	 * prints menu, checks if user wants to go again, prints post order
	 * tree, in order tree, and evaluated tree, and generates tree
	 * @param args
	 */
	public static void main(String[] args)
	{
		Stack stack = new Stack();
		System.out.println(MENU);
		boolean again = true;
		while (again)
		{
			ExpressionTree tree = generateTree(stack);
			System.out.println("Post order notation:\n" + tree);
			System.out.println("Post order notation: " + tree.stringValues());
			System.out.println("Answer: " + tree.evaluateTree());
			
			System.out.print("Go again? 'y' for yes: ");
			String input = SCAN.nextLine();
			if (input.charAt(0) == 'y')
			{
				again = true;
			}
			else
			{
				again = false;
			}
		}
	}

	/**
	 * gets type of token and creates a token depending on its type
	 * @return		token with type and value/op
	 */
	public static Token getToken()
	{
		Token token;
		char type = getType();
		System.out.println(type);
		if (type == '#')
		{
			token = new Token(Token.NUMBER, getNumber());
		}
		else if (type == 'b')
		{
			token = new Token(Token.BINARY, getBinaryOp());
		}
		else if (type == 'u')
		{
			token = new Token(Token.UNARY, getUnaryOp());
		}
		else
		{
			token = new Token(Token.END);
		}
		return token;
	}

	/**
	 * do while to get user input, calls checktype to check validity
	 * 
	 * @return		the user's command
	 */
	public static char getType()
	{
		boolean valid = true;
		String input = "";
		char type = '~';
		do
		{
			System.out.print("Enter Token type: ");
			input = SCAN.nextLine();
			if (input.equals(""))
			{
				System.out.println(ERROR + "Invalid Type, enter type again\n");
				valid = false;
			}
			else if (input.charAt(0) == 'h')
			{
				System.out.println(MENU);
				valid = false;
			}
			type = input.charAt(0);
			if (type == '#' || type == 'b' || type == 'u' || type == 'e')
			{
				return type;
			}
			else
			{
				System.out.println(ERROR + "Invalid type, enter type again\n");
				valid = false;
			}
		} while (!valid);
		return type;
	}

	/**
	 * gets double from user
	 * @return		user input - double
	 */
	public static double getNumber()
	{
		System.out.print("Enter a double: ");
		double input = SCAN.nextDouble();
		SCAN.nextLine();
		System.out.println(input);
		return input;
	}

	/**
	 * gets the binary op from the user or if incorrect, recursively calls
	 * itself
	 * @return		binary op
	 */
	public static int getBinaryOp()
	{
		char op = getOpChar();
		boolean valid = true;
		int type = -1;
		do
		{
			valid = true;
			if (op == '-')
			{
				type = Token.SUBTRACT;
			}
			else if (op == '+')
			{
				type = Token.ADD;
			}
			else if (op == '*')
			{
				type = Token.MULTIPLY;
			}
			else if (op == '/')
			{
				type = Token.DIVIDE;
			}
			else
			{
				System.out.println(ERROR + "Enter binary op again\n");
				valid = false;
				op = getOpChar();
			}
		} while (!valid);
		System.out.println(op);
		return type;
	}

	/**
	 * gets the unary op from the user or if incorrect, recursively calls
	 * itself
	 * @return		unary op
	 */
	public static int getUnaryOp()
	{
		char op = getOpChar();
		boolean valid = true;
		int type = -1;
		do
		{
			valid = true;
			if (op == '+')
			{
				type = Token.PLUS;
			}
			else if (op == '-')
			{
				type = Token.MINUS;
			}
			else if (op == 'q')
			{
				type = Token.SQRT;
			}
			else if (op == 'u')
			{
				type = Token.ABS;
			}
			else if (op == 'l')
			{
				type = Token.LOG;
			}
			else if (op == 'e')
			{
				type = Token.EXP;
			}
			else if (op == 's')
			{
				type = Token.SIN;
			}
			else if (op == 'c')
			{
				type = Token.COS;
			}
			else if (op == 't')
			{
				type = Token.TAN;
			}
			else
			{
				System.out.println(ERROR + "Enter Unary op again\n");
				valid = false;
				op = getOpChar();
			}
		} while (!valid);
		System.out.println(op);
		return type;
	}

	/**
	 * gets user input, checks to see if it is valid, else calls itself
	 * 
	 * @return		the user's command
	 */
	public static char getOpChar()
	{
		char op = '~';
		boolean valid = true;
		do
		{
			System.out.print("Enter op: ");
			String input = SCAN.nextLine();
			if (input.equals(""))
			{
				System.out.println(ERROR + "Enter op again");
				valid = false;
			}
			else
			{
				//command is first char of the input
				op = input.charAt(0);
				return op;
			}
		} while (!valid);
		return op;
	}

	/**
	 * inserts number token as head of an expressiontree
	 * @param stack		to push the number expressiontree
	 * @param token		token from generate tree
	 */
	public static void numberInsert(Token token, Stack stack)
	{
		stack.push(new ExpressionTree(token));
	}

	/**
	 * inserts unary token as head of expressiontree if it is valid
	 * @param stack 	stack to push/pop expressiontrees
	 * @param token		token from generate tree
	 */
	public static void unaryInsert(Token token, Stack stack)
	{
		ExpressionTree temp = (ExpressionTree) stack.pop();
		if (temp == null)
		{
			System.out.println(ERROR + "Enter a number first\n");
		}
		else
		{
			stack.push(new ExpressionTree(token, temp));
		}
	}

	/**
	 * inserts unary token as head of expressiontree if it is valid
	 * @param stack		stack to push/pop expressiontrees
	 * @param token		token from generate tree
	 */
	public static void binaryInsert(Token token, Stack stack)
	{
		ExpressionTree temp1 = (ExpressionTree) stack.pop();
		ExpressionTree temp2 = (ExpressionTree) stack.pop();
		if (temp1 == null || temp2 == null)
		{
			stack.push(temp1);
			System.out.println(ERROR + "Not enough tokens entered\n");
		}
		else
		{
			stack.push(new ExpressionTree(token, temp2, temp1));
		}
	}

	/**
	 * checks to see if end token can be used, if not returns false, else 
	 * returns true
	 * @param stack		to push/pop expressiontrees
	 * @param token		token from generate tree
	 * @return		whether end is valid
	 */
	public static boolean endInsert(Stack stack)
	{
		ExpressionTree temp1 = (ExpressionTree) stack.pop();
		ExpressionTree temp2 = (ExpressionTree) stack.pop();
		if (temp1 == null)
		{
			System.out.println(ERROR + "Nothing on the stack\n");
			return false;
		}
		else if (temp2 != null)
		{
			stack.push(temp2);
			stack.push(temp1);
			System.out.println(ERROR + "Only one token can remain to end\n");
			return false;
		}
		else
		{
			stack.push(temp1);
			return true;
		}
	}

	/**
	 * checks to see what type the token is, and calls respective method
	 * if type is end, checks to see if it can be called and returns it, or 
	 * reiterates through while loop until it is called validly
	 * @param stack		stack of expressiontrees
	 * @param variables		arraylist of variables in case variable token
	 * @return		final expressiontree
	 */
	public static ExpressionTree generateTree(Stack stack)
	{ 
		while (true)
		{
			Token now = getToken();
			//token is previous so that remove can be used
			if (now.getType() == Token.NUMBER)
			{
				numberInsert(now, stack);
			}
			else if (now.getType() == Token.UNARY)
			{
				unaryInsert(now, stack);
			}
			else if (now.getType() == Token.BINARY)
			{
				binaryInsert(now, stack);
			}
			else if (now.getType() == Token.END && endInsert(stack))
			{
				return (ExpressionTree) stack.pop();
			}
		}
	}
}
/******************************************************************************
 * Expression Tree
 * <p>
 * 		Three constructors for different kinds of expressiontrees, getRoot 
 * returns root of expressiontree. Evaluate calls auxEvaluate which checks if
 * it is binary or unary, which evaluate accordingly, and the toStrings return
 * postorder with the values and ops and what the actual ops are.
 *
 * ***************************************************************************/
class ExpressionTree
{
	private TreeNode myRoot;

	/**
	 * constructor for binary expressiontree
	 * @param root		token value
	 * @param left		left expressiontree
	 * @param right		right expressiontree
	 */
	public ExpressionTree(Token root, ExpressionTree left,
			ExpressionTree right)
	{
		myRoot = new TreeNode(root, left.myRoot, right.myRoot);
	}

	/**
	 * constructor for number expressiontree
	 * @param root		token value
	 */
	public ExpressionTree(Token root)
	{
		myRoot = new TreeNode(root, null, null);
	}

	/**
	 * constructor for unary expressiontree
	 * @param root		token value
	 * @param left		left expressiontree
	 */
	public ExpressionTree(Token root, ExpressionTree left)
	{
		myRoot = new TreeNode(root, left.myRoot, null);
	}

	/**
	 * toString
	 */
	public String toString()
	{
		return auxToStringPostOrder(myRoot);
	}

	/**
	 * Traverse the given subtree using postOrder and convert to a String
	 * @return			The subtree as a String traversed postOrder
	 */
	private String auxToStringPostOrder(TreeNode now)
	{
		if (now == null)
		{
			return "";
		}
		else
		{
			return 	auxToStringPostOrder(now.getLeft()) +  
					auxToStringPostOrder(now.getRight()) + 
					now.getValue().toString() + '\n';
		}
	}

	/**
	 * root accessor
	 * @return		TreeNode root
	 */
	public TreeNode getRoot()
	{
		return myRoot;
	}

	/**
	 * evaluates tokens in tree
	 * @return		evaluation of the expressiontree
	 */
	public double evaluateTree()
	{
		return auxEvaluateTree(myRoot);
	}

	/**
	 * returns evaluation of all expressiontrees below it
	 * @param now		current treenode
	 * @return		evaluated number
	 */
	private double auxEvaluateTree(TreeNode now)
	{
		Token token = (Token) now.getValue();
		if (token.getType() == Token.NUMBER)
		{
			return token.getValue();
		}
		else 
		{
			if (token.getType() == Token.BINARY)	
			{
				return evaluateBinary(now);
			}
			else
			{
				return evaluateUnary(now);
			}
		}
	}

	/**
	 * evalueates left and right and combines them depending on the op
	 * @param now		current treenode
	 * @return		evaluation
	 */
	private double evaluateBinary(TreeNode now)
	{
		double left = auxEvaluateTree(now.getLeft());
		double right = auxEvaluateTree(now.getRight());
		Token token = (Token) now.getValue();
		if (token.getOp() == Token.ADD)
		{
			return left + right;
		}
		else if (token.getOp() == Token.SUBTRACT)
		{
			return left - right;
		}
		else if (token.getOp() == Token.MULTIPLY)
		{
			return left * right;
		}
		else
		{
			return left / right;
		}
	}

	/**
	 * evaluates left expressiontree and modifies it depending on the op
	 * @param now		current expressiontree
	 * @return		evaluation
	 */
	private double evaluateUnary(TreeNode now)
	{
		double left = auxEvaluateTree(now.getLeft());
		Token token = (Token) now.getValue();
		if (token.getOp() == Token.PLUS)
		{
			return left;
		}
		else if (token.getOp() == Token.SQRT)
		{
			return Math.sqrt(left);
		}
		else if (token.getOp() == Token.ABS)
		{
			return Math.abs(left);
		}
		else if (token.getOp() == Token.LOG)
		{
			return Math.log(left);
		}
		else if (token.getOp() == Token.EXP)
		{
			return Math.exp(left);
		}
		else if (token.getOp() == Token.SIN)
		{
			return Math.sin(left);
		}
		else if (token.getOp() == Token.COS)
		{
			return Math.cos(left);
		}
		else if (token.getOp() == Token.TAN)
		{
			return Math.tan(left);
		}
		else
		{
			return 0 - left;
		}
	}

	/**
	 * prints post order tree's tokens but only their values (as in minus
	 * becomes '-')
	 * @return		combination of whole string
	 */
	public String stringValues()
	{
		return auxStringValues(myRoot);
	}

	/**
	 * prints post order tree's tokens but only their values (as in minus
	 * becomes '-')
	 * @return		combination of whole string below it and itself
	 */
	private String auxStringValues(TreeNode node)
	{
		if (node == null)
		{
			return "";
		}
		else
		{
			Token token = (Token) node.getValue();
			return auxStringValues(node.getLeft()) +
					auxStringValues(node.getRight()) +
					token.valueString();
		}
	}
}
/******************************************************************************
Class:		ListNode
Created by:	APCS

Description:
	The standard ListNode class assumed for the APCS exam.
	This can be used as a separate file or incorporated into you program file.  
	If you put it into another file then you must remove the "public" in front 
	of class ListNode. Has a value in each node and a pointer to the next node
 ******************************************************************************/
class ListNode 
{
	private Object value;
	private ListNode next;

	/**
	 * constructor, sets value and next
	 * 
	 * @param initVal		what is in the node
	 * @param initNext		pointer to next node
	 */
	public ListNode(Object initVal, ListNode initNext)
	{
		value = initVal;
		next = initNext;
	}

	/**
	 * returns the object in the node
	 * 
	 * @return		object value
	 */
	public Object getValue()
	{
		return value;
	}

	/**
	 * sets value 
	 * 
	 * @param newValue		what is to be set as in the node
	 */
	public void setValue(Object newValue)
	{
		value = newValue;
	}

	/**
	 * returns next
	 * 
	 * @return		pointer to next node
	 */
	public ListNode getNext()
	{
		return next;
	}

	/**
	 * sets next
	 * 
	 * @param newNext		new pointer
	 */
	public void setNext(ListNode newNext)
	{
		next = newNext;
	}
}

/**********************************************************************************************
Class:		Stack
Created by:	Richard Steinberg
Created on: 11/21/08
<p>
Description:
A class to implement a stack using a linked list of ListNodes. <p>
IMPORTANT NOTES: 
-- The Stack class protects itself.  The is no way to call it that causes an invalid Stack.
-- To keep this class useful in multiple applications the Stack class PRINTS NO ERROR MESSAGES.  
	Afer all, it doesn't know what the class that calls it wants to do or say in case of errors 
	(popping when empty.)  Only the calling class knows.  So this class is 	written so that the
	calling class can check for error cases and handle them however it wants.
-- Why have the size parameter in the constructor and the isFull() method?  These are here only
	so that this class is interchangeable with the array implementation of the Stack class that
		did need them.

 *********************************************************************************************/
class Stack
{
	private ListNode stackTop;

	/* Constructor.  The parameter size is received but ignored. */
	public Stack()
	{
		stackTop = null;
	}

	/* isEmpty - return true if empty, false otherwise. */
	public boolean isEmpty()
	{
		return stackTop == null;
	}

	/* isFull - never true for a linked list! */
	public boolean isFull()
	{
		return false;
	}

	/* push - Push s onto the stack.  Should never fail. */
	public void push(Object s)
	{
		stackTop = new ListNode(s, stackTop);
	}

	/* pop - If there is anything on the stack, pop the top and return it. */
	public Object pop()
	{
		if (!isEmpty())
		{
			Object temp = stackTop.getValue();
			stackTop = stackTop.getNext();
			return temp;
		}
		else
		{
			return null;
		}
	}

	/* toString - Convert the stack to a string and return it. */
	public String toString()
	{
		String stackAsString = "";
		ListNode current = stackTop;
		int index = 1;
		while(current != null)
		{
			stackAsString = stackAsString + index + " : " + 
					current.getValue() + '\n';
			current = current.getNext();
			index++;
		}
		return stackAsString;
	}
}

/******************************************************************************
 * Token Class
 * <p>
 * 		4 types, many unary operations and binary operations too. There are
 * 2 toStrings, one states the type and op in int form, while valueString 
 * writes what they are - plus becomes '+'.
 *
 * ****************************************************************************/
class Token 
{
	private int myType;
	private double myValue;
	private int myOp;
	// Types
	public static final int NUMBER = 1;
	public static final int UNARY = 2;
	public static final int BINARY = 3;
	public static final int END = 4;
	// Unary Ops
	public static final int PLUS = 5;
	public static final int MINUS = 6;
	public static final int SQRT = 11;
	public static final int ABS = 12;
	public static final int LOG = 13;
	public static final int EXP = 14;
	public static final int SIN = 15;
	public static final int COS = 16;
	public static final int TAN = 17;
	// Binary Ops
	public static final int ADD = 7;
	public static final int SUBTRACT = 8;
	public static final int MULTIPLY = 9;
	public static final int DIVIDE = 10;

	/**
	 * constructor for number token
	 * @param type		type
	 * @param value		number
	 */
	public Token(int type, double value)
	{
		this.myType = type;
		this.myValue = value;
	}

	/**
	 * constructor for unary or binary op
	 * @param type		type
	 * @param op		binary or unary operation
	 */
	public Token(int type, int op)
	{
		this.myType = type;
		this.myOp = op;
	}

	/**
	 * end token constructor
	 * @param end 		type 
	 */
	public Token(int type)
	{
		this.myType = type;
	}

	/**
	 * returns type and op (in number form), or type and number
	 */
	public String toString()
	{
		if (myType != NUMBER)
		{
			return "Type: " + myType + "\nOp: " + myOp;
		}
		else
		{
			return "Type: " + myType + "\nValue: " + myValue;
		}	
	}

	/**
	 * makes a string that is just the token's value
	 * @return		value field of token
	 */
	public String valueString()
	{
		if (myType == NUMBER)
		{
			return "(# " + myValue + ") ";
		}
		else if (myOp == MINUS)
		{
			return "(u -) ";
		}
		else if (myOp == SUBTRACT)
		{
			return "(b -) ";
		}
		else if (myOp == ADD)
		{
			return "(b +) ";
		}
		else if (myOp == PLUS)
		{
			return "(u +) ";
		}
		else if (myOp == MULTIPLY)
		{
			return "(b *) ";
		}
		else if (myOp == SQRT)
		{
			return "(u SQRT()) ";
		}
		else if (myOp == ABS)
		{
			return "(u ABS()) ";
		}
		else if (myOp == LOG)
		{
			return "(u ln()) ";
		}
		else if (myOp == EXP)
		{
			return "(u e^()) ";
		}
		else if (myOp == SIN)
		{
			return "(u sin()) ";
		}
		else if (myOp == COS)
		{
			return "(u cos()) ";
		}
		else if (myOp == TAN)
		{
			return "(u tan()) ";
		}
		else
		{
			return "(b /) ";
		}
	}

	/**
	 * type accessor
	 * @return		type (as an int)
	 */
	public int getType()
	{
		return myType;
	}

	/**
	 * op accessor
	 * @return		op (as an int)
	 */
	public int getOp()
	{
		return myOp;
	}

	/**
	 * number accessor
	 * @return		number
	 */
	public double getValue()
	{
		return myValue;
	}
}

/*************************************************************
 * TreeNode - a single node in a tree structure, containing
 * 			  a value and two child pointers
 **************************************************************/
class TreeNode
{
	/** The value stored in this node. */
	private Object value;

	/** This node's left child (may be null) */
	private TreeNode left;

	/** This node's right child (may be null) */
	private TreeNode right;

	/**
	 * Construct a TreeNode with the given value and children
	 * 
	 * @param v			Comparable value
	 * @param l			left child (or null)
	 * @param r			right child (or null)
	 */
	public TreeNode(Object v, TreeNode l, TreeNode r)
	{
		value = v;
		left = l;
		right = r;
	}

	/**
	 * Get the node's value
	 * 
	 * @return		node's value
	 */
	public Object getValue()
	{
		return value;
	}

	/**
	 * Get the node's left child
	 * @return		node's left child (or null)
	 */
	public TreeNode getLeft()
	{
		return left;
	}

	/**
	 * Get the node's right child
	 * @return		node's right child (or null)
	 */
	public TreeNode getRight()
	{
		return right;
	}

	/**
	 * Set the node's value
	 * 
	 * @param v		node's new value
	 */
	public void setValue(Object v)
	{
		value = v;
	}

	/**
	 * Set the node's left child
	 * @param l		node's new left child (may be null)
	 */
	public void setLeft(TreeNode l)
	{
		left = l;
	}

	/**
	 * Set the node's right child
	 * @param l		node's new right child (may be null)
	 */
	public void setRight(TreeNode r)
	{
		right = r;
	}
}