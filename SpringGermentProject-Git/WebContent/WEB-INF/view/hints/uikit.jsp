<jsp:include page="../include/header.jsp" />
<div class="page-wrapper">
	<div class="content container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<div class="card-box">
					<h4 class="card-title">Default Button</h4>
					<p>
						<button type="button" class="btn btn-primary">Primary</button>
						<button type="button" class="btn btn-secondary">Secondary</button>
						<button type="button" class="btn btn-success">Success</button>
						<button type="button" class="btn btn-danger">Danger</button>
						<button type="button" class="btn btn-warning">Warning</button>
						<button type="button" class="btn btn-info">Info</button>
						<button type="button" class="btn btn-light">Light</button>
						<button type="button" class="btn btn-dark">Dark</button>

						<button type="button" class="btn btn-link">Link</button>
					</p>

					<hr>
					<h4 class="card-title">Outline buttons</h4>
					<p>
						<button type="button" class="btn btn-outline-primary">Primary</button>
						<button type="button" class="btn btn-outline-secondary">Secondary</button>
						<button type="button" class="btn btn-outline-success">Success</button>
						<button type="button" class="btn btn-outline-danger">Danger</button>
						<button type="button" class="btn btn-outline-warning">Warning</button>
						<button type="button" class="btn btn-outline-info">Info</button>
						<button type="button" class="btn btn-outline-light">Light</button>
						<button type="button" class="btn btn-outline-dark">Dark</button>
					</p>

					<hr>
					<h4 class="card-title">Button Sizes</h4>
					<p>
					<h6 class="card-title">
						<u>Large Button</u>
					</h6>
					<button type="button" class="btn btn-primary btn-lg">Primary</button>
					<button type="button" class="btn btn-secondary btn-lg">Secondary</button>
					<button type="button" class="btn btn-success btn-lg">Success</button>
					<button type="button" class="btn btn-danger btn-lg">Danger</button>
					<button type="button" class="btn btn-warning btn-lg">Warning</button>
					<button type="button" class="btn btn-info btn-lg">Info</button>
					<button type="button" class="btn btn-light btn-lg">Light</button>
					<button type="button" class="btn btn-dark btn-lg">Dark</button>

					<button type="button" class="btn btn-link btn-lg">Link</button>
					</p>
					<p>
					<h6 class="card-title">
						<u>Default Button Size</u>
					</h6>
					<button type="button" class="btn btn-default">Button</button>
					<button type="button" class="btn btn-primary">Button</button>
					<button type="button" class="btn btn-success">Button</button>
					<button type="button" class="btn btn-info">Button</button>
					<button type="button" class="btn btn-warning">Button</button>
					<button type="button" class="btn btn-danger">Button</button>
					</p>
					<p>
					<h6 class="card-title">
						<u>Small Button Size</u>
					</h6>
					<button type="button" class="btn btn-default btn-sm">Button</button>
					<button type="button" class="btn btn-primary btn-sm">Button</button>
					<button type="button" class="btn btn-success btn-sm">Button</button>
					<button type="button" class="btn btn-info btn-sm">Button</button>
					<button type="button" class="btn btn-warning btn-sm">Button</button>
					<button type="button" class="btn btn-danger btn-sm">Button</button>
					</p>
					<p>
					<h6 class="card-title">
						<u>Extra Small Button Size</u>
					</h6>
					<button type="button" class="btn btn-default btn-xs">Button</button>
					<button type="button" class="btn btn-primary btn-xs">Button</button>
					<button type="button" class="btn btn-success btn-xs">Button</button>
					<button type="button" class="btn btn-info btn-xs">Button</button>
					<button type="button" class="btn btn-warning btn-xs">Button</button>
					<button type="button" class="btn btn-danger btn-xs">Button</button>
					</p>
					<hr>
					<h4 class="card-title">Button Groups</h4>
					<div class="btn-toolbar">
						<div class="btn-group btn-group-lg">
							<button type="button" class="btn btn-primary">Left</button>
							<button type="button" class="btn btn-primary">Middle</button>
							<button type="button" class="btn btn-primary">Right</button>
						</div>
					</div>
					<br>
					<div class="btn-toolbar">
						<div class="btn-group">
							<button type="button" class="btn btn-primary">Left</button>
							<button type="button" class="btn btn-primary">Middle</button>
							<button type="button" class="btn btn-primary">Right</button>
						</div>
					</div>
					<br>
					<div class="btn-toolbar">
						<div class="btn-group btn-group-sm">
							<button type="button" class="btn btn-primary">Left</button>
							<button type="button" class="btn btn-primary">Middle</button>
							<button type="button" class="btn btn-primary">Right</button>
						</div>
					</div>
					<br>
					<div class="btn-toolbar">
						<div class="btn-group btn-group-xs">
							<button type="button" class="btn btn-primary">Left</button>
							<button type="button" class="btn btn-primary">Middle</button>
							<button type="button" class="btn btn-primary">Right</button>
						</div>
					</div>
				</div>
				<div class="card-box">
					<h4 class="card-title">Alerts</h4>
					<div class="alert alert-primary" role="alert">A simple
						primary alert—check it out!</div>
					<div class="alert alert-secondary" role="alert">A simple
						secondary alert—check it out!</div>
					<div class="alert alert-success" role="alert">A simple
						success alert—check it out!</div>
					<div class="alert alert-danger" role="alert">A simple danger
						alert—check it out!</div>
					<div class="alert alert-warning" role="alert">A simple
						warning alert—check it out!</div>
					<div class="alert alert-info" role="alert">A simple info
						alert—check it out!</div>
					<div class="alert alert-light" role="alert">A simple light
						alert—check it out!</div>
					<div class="alert alert-dark" role="alert">A simple dark
						alert—check it out!</div>
				</div>
				<div class="card-box">
					<h4 class="card-title">Dropdowns within Text</h4>
					<!-- <div class="card-body position-relative"> -->
					<div class="dropdown">
						<button class="btn btn-secondary dropdown-toggle" type="button"
							id="testDrop1" data-toggle="dropdown"
							aria-haspopup="true" aria-expanded="false">Dropdown
							button</button>
						<div class="dropdown-menu" aria-labelledby="testDrop1">
							<a class="dropdown-item" href="#">Action</a> <a
								class="dropdown-item" href="#">Another action</a> <a
								class="dropdown-item" href="#">Something else here</a>
						</div>
					</div>
					<!--</div>  -->
					<hr>
					<h4 class="card-title">Dropdowns within Buttons</h4>
					<div class="btn-group">
						<button type="button" data-toggle="dropdown"
							class="btn btn-default dropdown-toggle">
							Action <span class="caret"></span>
						</button>
						<ul class="dropdown-menu">
							<li><a href="#">Action</a></li>
							<li><a href="#">Another action</a></li>
							<li class="divider"></li>
							<li><a href="#">Separated link</a></li>
						</ul>
					</div>
					<div class="btn-group">
						<button type="button" data-toggle="dropdown"
							class="btn btn-primary dropdown-toggle">
							Action <span class="caret"></span>
						</button>
						<ul class="dropdown-menu">
							<li><a href="#">Action</a></li>
							<li><a href="#">Another action</a></li>
							<li class="divider"></li>
							<li><a href="#">Separated link</a></li>
						</ul>
					</div>
					<div class="btn-group">
						<button type="button" data-toggle="dropdown"
							class="btn btn-info dropdown-toggle">
							Action <span class="caret"></span>
						</button>
						<ul class="dropdown-menu">
							<li><a href="#">Action</a></li>
							<li><a href="#">Another action</a></li>
							<li class="divider"></li>
							<li><a href="#">Separated link</a></li>
						</ul>
					</div>
					<div class="btn-group">
						<button type="button" data-toggle="dropdown"
							class="btn btn-success dropdown-toggle">
							Action <span class="caret"></span>
						</button>
						<ul class="dropdown-menu">
							<li><a href="#">Action</a></li>
							<li><a href="#">Another action</a></li>
							<li class="divider"></li>
							<li><a href="#">Separated link</a></li>
						</ul>
					</div>
					<div class="btn-group">
						<button type="button" data-toggle="dropdown"
							class="btn btn-warning dropdown-toggle">
							Action <span class="caret"></span>
						</button>
						<ul class="dropdown-menu">
							<li><a href="#">Action</a></li>
							<li><a href="#">Another action</a></li>
							<li class="divider"></li>
							<li><a href="#">Separated link</a></li>
						</ul>
					</div>
					<div class="btn-group">
						<button type="button" data-toggle="dropdown"
							class="btn btn-danger dropdown-toggle">
							Action <span class="caret"></span>
						</button>
						<ul class="dropdown-menu">
							<li><a href="#">Action</a></li>
							<li><a href="#">Another action</a></li>
							<li class="divider"></li>
							<li><a href="#">Separated link</a></li>
						</ul>
					</div>
					<hr>
					<h4 class="card-title">Split button dropdowns</h4>
					<div class="btn-group">
						<button type="button" class="btn btn-default">Action</button>
						<button type="button" data-toggle="dropdown"
							class="btn btn-default dropdown-toggle">
							<span class="caret"></span>
						</button>
						<ul class="dropdown-menu">
							<li><a href="#">Action</a></li>
							<li><a href="#">Another action</a></li>
							<li class="divider"></li>
							<li><a href="#">Separated link</a></li>
						</ul>
					</div>
					<div class="btn-group">
						<button type="button" class="btn btn-primary">Action</button>
						<button type="button" data-toggle="dropdown"
							class="btn btn-primary dropdown-toggle">
							<span class="caret"></span>
						</button>
						<ul class="dropdown-menu">
							<li><a href="#">Action</a></li>
							<li><a href="#">Another action</a></li>
							<li class="divider"></li>
							<li><a href="#">Separated link</a></li>
						</ul>
					</div>
					<div class="btn-group">
						<button type="button" class="btn btn-info">Info</button>
						<button type="button" data-toggle="dropdown"
							class="btn btn-info dropdown-toggle">
							<span class="caret"></span>
						</button>
						<ul class="dropdown-menu">
							<li><a href="#">Action</a></li>
							<li><a href="#">Another action</a></li>
							<li class="divider"></li>
							<li><a href="#">Separated link</a></li>
						</ul>
					</div>
					<div class="btn-group">
						<button type="button" class="btn btn-success">Success</button>
						<button type="button" data-toggle="dropdown"
							class="btn btn-success dropdown-toggle">
							<span class="caret"></span>
						</button>
						<ul class="dropdown-menu">
							<li><a href="#">Action</a></li>
							<li><a href="#">Another action</a></li>
							<li class="divider"></li>
							<li><a href="#">Separated link</a></li>
						</ul>
					</div>
					<div class="btn-group">
						<button type="button" class="btn btn-warning">Warning</button>
						<button type="button" data-toggle="dropdown"
							class="btn btn-warning dropdown-toggle">
							<span class="caret"></span>
						</button>
						<ul class="dropdown-menu">
							<li><a href="#">Action</a></li>
							<li><a href="#">Another action</a></li>
							<li class="divider"></li>
							<li><a href="#">Separated link</a></li>
						</ul>
					</div>
					<div class="btn-group">
						<button type="button" class="btn btn-danger">Danger</button>
						<button type="button" data-toggle="dropdown"
							class="btn btn-danger dropdown-toggle">
							<span class="caret"></span>
						</button>
						<ul class="dropdown-menu">
							<li><a href="#">Action</a></li>
							<li><a href="#">Another action</a></li>
							<li class="divider"></li>
							<li><a href="#">Separated link</a></li>
						</ul>
					</div>
				</div>
				<div class="card-box">
					<h4 class="card-title">Progress Bars</h4>
					<h5 class="text-muted">Default Progress Bars</h5>
					<div class="row">
						<div class="col-lg-6">
							<div class="progress mb-2">
								<div class="progress-bar" role="progressbar" aria-valuenow="0"
									aria-valuemin="0" aria-valuemax="100"></div>
							</div>
							<div class="progress mb-2">
								<div class="progress-bar" role="progressbar" style="width: 25%"
									aria-valuenow="25" aria-valuemin="0" aria-valuemax="100"></div>
							</div>
							<div class="progress mb-2">
								<div class="progress-bar" role="progressbar" style="width: 50%"
									aria-valuenow="50" aria-valuemin="0" aria-valuemax="100"></div>
							</div>
							<div class="progress mb-2">
								<div class="progress-bar" role="progressbar" style="width: 75%"
									aria-valuenow="75" aria-valuemin="0" aria-valuemax="100"></div>
							</div>
							<div class="progress mb-2">
								<div class="progress-bar" role="progressbar" style="width: 100%"
									aria-valuenow="100" aria-valuemin="0" aria-valuemax="100"></div>
							</div>
						</div>
						<div class="col-lg-6">
							<div class="progress mb-2">
								<div class="progress-bar progress-bar-striped"
									role="progressbar" style="width: 10%" aria-valuenow="10"
									aria-valuemin="0" aria-valuemax="100"></div>
							</div>
							<div class="progress mb-2">
								<div class="progress-bar progress-bar-striped bg-success"
									role="progressbar" style="width: 25%" aria-valuenow="25"
									aria-valuemin="0" aria-valuemax="100"></div>
							</div>
							<div class="progress mb-2">
								<div class="progress-bar progress-bar-striped bg-info"
									role="progressbar" style="width: 50%" aria-valuenow="50"
									aria-valuemin="0" aria-valuemax="100"></div>
							</div>
							<div class="progress mb-2">
								<div class="progress-bar progress-bar-striped bg-warning"
									role="progressbar" style="width: 75%" aria-valuenow="75"
									aria-valuemin="0" aria-valuemax="100"></div>
							</div>
							<div class="progress mb-2">
								<div class="progress-bar progress-bar-striped bg-danger"
									role="progressbar" style="width: 100%" aria-valuenow="100"
									aria-valuemin="0" aria-valuemax="100"></div>
							</div>
						</div>
					</div>
					<h5 class="text-muted">Small Progress Bars</h5>
					<div class="row">
						<div class="col-lg-6">
							<div>
								<div class="progress progress-sm mb-2">
									<div class="progress-bar" role="progressbar" style="width: 10%"
										aria-valuenow="75" aria-valuemin="0" aria-valuemax="100"></div>
								</div>
								<div class="progress progress-sm mb-2">
									<div class="progress-bar bg-success" role="progressbar"
										style="width: 25%" aria-valuenow="25" aria-valuemin="0"
										aria-valuemax="100"></div>
								</div>
								<div class="progress progress-sm mb-2">
									<div class="progress-bar bg-info" role="progressbar"
										style="width: 50%" aria-valuenow="50" aria-valuemin="0"
										aria-valuemax="100"></div>
								</div>
								<div class="progress progress-sm mb-2">
									<div class="progress-bar bg-warning" role="progressbar"
										style="width: 75%" aria-valuenow="75" aria-valuemin="0"
										aria-valuemax="100"></div>
								</div>
								<div class="progress progress-sm mb-2">
									<div class="progress-bar bg-danger" role="progressbar"
										style="width: 100%" aria-valuenow="100" aria-valuemin="0"
										aria-valuemax="100"></div>
								</div>
							</div>
						</div>
						<div class="col-lg-6">
							<div class="progress progress-sm mb-2">
								<div class="progress-bar progress-bar-striped"
									role="progressbar" style="width: 10%" aria-valuenow="10"
									aria-valuemin="0" aria-valuemax="100"></div>
							</div>
							<div class="progress progress-sm mb-2">
								<div class="progress-bar progress-bar-striped bg-success"
									role="progressbar" style="width: 25%" aria-valuenow="25"
									aria-valuemin="0" aria-valuemax="100"></div>
							</div>
							<div class="progress progress-sm mb-2">
								<div class="progress-bar progress-bar-striped bg-info"
									role="progressbar" style="width: 50%" aria-valuenow="50"
									aria-valuemin="0" aria-valuemax="100"></div>
							</div>
							<div class="progress progress-sm mb-2">
								<div class="progress-bar progress-bar-striped bg-warning"
									role="progressbar" style="width: 75%" aria-valuenow="75"
									aria-valuemin="0" aria-valuemax="100"></div>
							</div>
							<div class="progress progress-sm mb-2">
								<div class="progress-bar progress-bar-striped bg-danger"
									role="progressbar" style="width: 100%" aria-valuenow="100"
									aria-valuemin="0" aria-valuemax="100"></div>
							</div>
						</div>
					</div>
					<h5 class="text-muted">Extra Small Progress Bars</h5>
					<div class="row">
						<div class="col-lg-6">
							<div>
								<div class="progress progress-xs mb-2">
									<div class="progress-bar w-75" role="progressbar"
										style="width: 10%" aria-valuenow="75" aria-valuemin="0"
										aria-valuemax="100"></div>
								</div>
								<div class="progress progress-xs mb-2">
									<div class="progress-bar bg-success" role="progressbar"
										style="width: 25%" aria-valuenow="25" aria-valuemin="0"
										aria-valuemax="100"></div>
								</div>
								<div class="progress progress-xs mb-2">
									<div class="progress-bar bg-info" role="progressbar"
										style="width: 50%" aria-valuenow="50" aria-valuemin="0"
										aria-valuemax="100"></div>
								</div>
								<div class="progress progress-xs mb-2">
									<div class="progress-bar bg-warning" role="progressbar"
										style="width: 75%" aria-valuenow="75" aria-valuemin="0"
										aria-valuemax="100"></div>
								</div>
								<div class="progress progress-xs mb-2">
									<div class="progress-bar bg-danger" role="progressbar"
										style="width: 100%" aria-valuenow="100" aria-valuemin="0"
										aria-valuemax="100"></div>
								</div>
							</div>
						</div>
						<div class="col-lg-6">
							<div class="progress progress-xs mb-2">
								<div class="progress-bar progress-bar-striped"
									role="progressbar" style="width: 10%" aria-valuenow="10"
									aria-valuemin="0" aria-valuemax="100"></div>
							</div>
							<div class="progress progress-xs mb-2">
								<div class="progress-bar progress-bar-striped bg-success"
									role="progressbar" style="width: 25%" aria-valuenow="25"
									aria-valuemin="0" aria-valuemax="100"></div>
							</div>
							<div class="progress progress-xs mb-2">
								<div class="progress-bar progress-bar-striped bg-info"
									role="progressbar" style="width: 50%" aria-valuenow="50"
									aria-valuemin="0" aria-valuemax="100"></div>
							</div>
							<div class="progress progress-xs mb-2">
								<div class="progress-bar progress-bar-striped bg-warning"
									role="progressbar" style="width: 75%" aria-valuenow="75"
									aria-valuemin="0" aria-valuemax="100"></div>
							</div>
							<div class="progress progress-xs mb-2">
								<div class="progress-bar progress-bar-striped bg-danger"
									role="progressbar" style="width: 100%" aria-valuenow="100"
									aria-valuemin="0" aria-valuemax="100"></div>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-lg-6">
						<div class="card-box pagination-box">
							<h4 class="card-title">Pagination</h4>
							<div>
								<nav aria-label="Page navigation example">
									<ul class="pagination">
										<li class="page-item"><a class="page-link" href="#">Previous</a></li>
										<li class="page-item"><a class="page-link" href="#">1</a></li>
										<li class="page-item"><a class="page-link" href="#">2</a></li>
										<li class="page-item"><a class="page-link" href="#">3</a></li>
										<li class="page-item"><a class="page-link" href="#">Next</a></li>
									</ul>
								</nav>
							</div>
							<div>
								<nav aria-label="Page navigation example">
									<ul class="pagination">
										<li class="page-item"><a class="page-link" href="#"
											aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
										</a></li>
										<li class="page-item"><a class="page-link" href="#">1</a></li>
										<li class="page-item"><a class="page-link" href="#">2</a></li>
										<li class="page-item"><a class="page-link" href="#">3</a></li>
										<li class="page-item"><a class="page-link" href="#"
											aria-label="Next"> <span aria-hidden="true">&raquo;</span>
										</a></li>
									</ul>
								</nav>
							</div>
							<div>
								<nav aria-label="...">
									<ul class="pagination">
										<li class="page-item disabled"><a class="page-link"
											href="#" tabindex="-1" aria-disabled="true">Previous</a></li>
										<li class="page-item"><a class="page-link" href="#">1</a></li>
										<li class="page-item active" aria-current="page"><a
											class="page-link" href="#">2 <span class="sr-only">(current)</span></a>
										</li>
										<li class="page-item"><a class="page-link" href="#">3</a></li>
										<li class="page-item"><a class="page-link" href="#">Next</a>
										</li>
									</ul>
								</nav>
							</div>
							<div>
								<ul class="pagination pagination-sm">
									<li class="page-item disabled"><a class="page-link"
										href="#">Previous</a></li>
									<li class="page-item"><a class="page-link" href="#">1</a></li>
									<li class="page-item"><a class="page-link" href="#">2</a></li>
									<li class="page-item"><a class="page-link" href="#">3</a></li>
									<li class="page-item"><a class="page-link" href="#">Next</a>
									</li>
								</ul>
							</div>
						</div>
					</div>
					<div class="col-lg-6">
						<div class="card-box">
							<h4 class="card-title">Breadcrumbs</h4>
							<nav aria-label="breadcrumb">
								<ol class="breadcrumb">
									<li class="breadcrumb-item active" aria-current="page">Home</li>
								</ol>
							</nav>

							<nav aria-label="breadcrumb">
								<ol class="breadcrumb">
									<li class="breadcrumb-item"><a href="#">Home</a></li>
									<li class="breadcrumb-item active" aria-current="page">Library</li>
								</ol>
							</nav>

							<nav aria-label="breadcrumb">
								<ol class="breadcrumb">
									<li class="breadcrumb-item"><a href="#">Home</a></li>
									<li class="breadcrumb-item"><a href="#">Library</a></li>
									<li class="breadcrumb-item active" aria-current="page">Data</li>
								</ol>
							</nav>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<jsp:include page="../include/footer.jsp" />