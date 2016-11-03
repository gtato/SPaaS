package containers;

public class Request {
	private String method;
	private Body body;
	
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Body getBody() {
		return body;
	}

	public void setBody(Body body) {
		this.body = body;
	}

	public Request(String method, Body body) {
		super();
		this.method = method;
		this.body = body;
	}
}
