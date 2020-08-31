<jsp:include page="../include/header.jsp" />
        <div class="page-wrapper">
            <div class="content container-fluid">
                <div class="row">
                    <div class="col-xs-12">
                        <h4 class="page-title">Tabs</h4>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6">
                        <div class="card-box">
                            <h4 class="card-title">Basic tabs</h4>
                            <nav>
                                <div class="nav nav-tabs" id="nav-tab" role="tablist">
                                    <a class="nav-item nav-link active" id="nav-home-tab" data-toggle="tab" href="#nav-home" role="tab" aria-controls="nav-home" aria-selected="true">Home</a>
                                    <a class="nav-item nav-link" id="nav-profile-tab" data-toggle="tab" href="#nav-profile" role="tab" aria-controls="nav-profile" aria-selected="false">Profile</a>
                                    <a class="nav-item nav-link" id="nav-contact-tab" data-toggle="tab" href="#nav-contact" role="tab" aria-controls="nav-contact" aria-selected="false">Contact</a>
                                </div>
                            </nav>
                            <div class="tab-content" id="nav-tabContent">
                                <div class="tab-pane fade show active" id="nav-home" role="tabpanel" aria-labelledby="nav-home-tab">Tab content 1</div>
                                <div class="tab-pane fade" id="nav-profile" role="tabpanel" aria-labelledby="nav-profile-tab">Tab content 2</div>
                                <div class="tab-pane fade" id="nav-contact" role="tabpanel" aria-labelledby="nav-contact-tab">Tab content 3</div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="card-box">
                            <h4 class="card-title">Basic justified tabs</h4>
                            <ul class="nav nav-tabs nav-justified" role="tablist">
                                <li class="nav-item  active">
                                    <a class="nav-link" href="#basic-justified-tab1" role="tab" aria-controls="nav-home" aria-selected="true">Home</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="#basic-justified-tab2" role="tab" aria-controls="nav-home" aria-selected="true">Profile</a>
                                </li>
                                <li class="nav-item dropdown">
                                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">Dropdown <span class="caret"></span></a>
                                    <ul class="dropdown-menu dropdown-menu-right">
                                        <li><a href="#basic-justified-tab3" data-toggle="tab">Dropdown 1</a></li>
                                        <li><a href="#basic-justified-tab4" data-toggle="tab">Dropdown 2</a></li>
                                    </ul>
                                </li>
                            </ul>
                            <div class="tab-content">
                                <div class="tab-pane active" id="basic-justified-tab1">
                                    Tab content 1
                                </div>
                                <div class="tab-pane" id="basic-justified-tab2">
                                    Tab content 2
                                </div>
                                <div class="tab-pane" id="basic-justified-tab3">
                                    Tab content 3
                                </div>
                                <div class="tab-pane" id="basic-justified-tab4">
                                    Tab content 4
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-6">
                        <div class="card-box">
                            <h4 class="card-title">Top line tabs</h4>
                            <ul class="nav nav-tabs nav-tabs-top">
                                <li class="active"><a href="#top-tab1" data-toggle="tab">Home</a></li>
                                <li><a href="#top-tab2" data-toggle="tab">Profile</a></li>
                                <li><a href="#top-tab3" data-toggle="tab">Messages</a></li>
                            </ul>
                            <div class="tab-content">
                                <div class="tab-pane active" id="top-tab1">
                                    Tab content 1
                                </div>
                                <div class="tab-pane" id="top-tab2">
                                    Tab content 2
                                </div>
                                <div class="tab-pane" id="top-tab3">
                                    Tab content 3
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="col-md-6">
                        <div class="card-box">
                            <h4 class="card-title">Top line justified</h4>
                            <ul class="nav nav-tabs nav-tabs-top nav-justified">
                                <li class="active"><a href="#top-justified-tab1" data-toggle="tab">Home</a></li>
                                <li><a href="#top-justified-tab2" data-toggle="tab">Profile</a></li>
                                <li><a href="#top-justified-tab3" data-toggle="tab">Messages</a></li>
                            </ul>
                            <div class="tab-content">
                                <div class="tab-pane active" id="top-justified-tab1">
                                    Tab content 1
                                </div>
                                <div class="tab-pane" id="top-justified-tab2">
                                    Tab content 2
                                </div>

                                <div class="tab-pane" id="top-justified-tab3">
                                    Tab content 3
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-6">
                        <div class="card-box">
                            <h6 class="card-title">Bottom line tabs</h6>
                            <ul class="nav nav-tabs nav-tabs-bottom">
                                <li class="active"><a href="#bottom-tab1" data-toggle="tab">Home</a></li>
                                <li><a href="#bottom-tab2" data-toggle="tab">Profile</a></li>
                                <li><a href="#bottom-tab3" data-toggle="tab">Messages</a></li>
                            </ul>

                            <div class="tab-content">
                                <div class="tab-pane active" id="bottom-tab1">
                                    Tab content 1
                                </div>
                                <div class="tab-pane" id="bottom-tab2">
                                    Tab content 2
                                </div>
                                <div class="tab-pane" id="bottom-tab3">
                                    Tab content 3
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="card-box">
                            <h6 class="card-title">Bottom line justified</h6>
                            <ul class="nav nav-tabs nav-tabs-bottom nav-justified">
                                <li class="active"><a href="#bottom-justified-tab1" data-toggle="tab">Home</a></li>
                                <li><a href="#bottom-justified-tab2" data-toggle="tab">Profile</a></li>
                                <li><a href="#bottom-justified-tab3" data-toggle="tab">Messages</a></li>
                            </ul>
                            <div class="tab-content">
                                <div class="tab-pane active" id="bottom-justified-tab1">
                                    Tab content 1
                                </div>
                                <div class="tab-pane" id="bottom-justified-tab2">
                                    Tab content 2
                                </div>
                                <div class="tab-pane" id="bottom-justified-tab3">
                                    Tab content 3
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6">
                        <div class="card-box">
                            <h4 class="card-title">Solid tabs</h4>
                            <ul class="nav nav-tabs nav-tabs-solid">
                                <li class="active"><a href="#solid-tab1" data-toggle="tab">Home</a></li>
                                <li><a href="#solid-tab2" data-toggle="tab">Profile</a></li>
                                <li><a href="#solid-tab3" data-toggle="tab">Messages</a></li>
                            </ul>
                            <div class="tab-content">
                                <div class="tab-pane active" id="solid-tab1">
                                    Tab content 1
                                </div>
                                <div class="tab-pane" id="solid-tab2">
                                    Tab content 2
                                </div>
                                <div class="tab-pane" id="solid-tab3">
                                    Tab content 3
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="col-md-6">
                        <div class="card-box">
                            <h4 class="card-title">Solid justified</h4>
                            <ul class="nav nav-tabs nav-tabs-solid nav-justified">
                                <li class="active"><a href="#solid-justified-tab1" data-toggle="tab">Home</a></li>
                                <li><a href="#solid-justified-tab2" data-toggle="tab">Profile</a></li>
                                <li><a href="#solid-justified-tab3" data-toggle="tab">Messages</a></li>
                            </ul>
                            <div class="tab-content">
                                <div class="tab-pane active" id="solid-justified-tab1">
                                    Tab content 1
                                </div>
                                <div class="tab-pane" id="solid-justified-tab2">
                                    Tab content 2
                                </div>
                                <div class="tab-pane" id="solid-justified-tab3">
                                    Tab content 3
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6">
                        <div class="card-box">
                            <h4 class="card-title">Solid Rounded</h4>
                            <ul class="nav nav-tabs nav-tabs-solid nav-tabs-rounded">
                                <li class="active"><a href="#solid-rounded-tab1" data-toggle="tab">Home</a></li>
                                <li><a href="#solid-rounded-tab2" data-toggle="tab">Profile</a></li>
                                <li><a href="#solid-rounded-tab3" data-toggle="tab">Messages</a></li>
                            </ul>
                            <div class="tab-content">
                                <div class="tab-pane active" id="solid-rounded-tab1">
                                    Tab content 1
                                </div>
                                <div class="tab-pane" id="solid-rounded-tab2">
                                    Tab content 2
                                </div>
                                <div class="tab-pane" id="solid-rounded-tab3">
                                    Tab content 3
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="card-box">
                            <h4 class="card-title">Rounded justified</h4>
                            <ul class="nav nav-tabs nav-tabs-solid nav-tabs-rounded nav-justified">
                                <li class="active"><a href="#solid-rounded-justified-tab1" data-toggle="tab">Home</a></li>
                                <li><a href="#solid-rounded-justified-tab2" data-toggle="tab">Profile</a></li>
                                <li><a href="#solid-rounded-justified-tab3" data-toggle="tab">Messages</a></li>
                            </ul>
                            <div class="tab-content">
                                <div class="tab-pane active" id="solid-rounded-justified-tab1">
                                    Tab content 1
                                </div>
                                <div class="tab-pane" id="solid-rounded-justified-tab2">
                                    Tab content 2
                                </div>
                                <div class="tab-pane" id="solid-rounded-justified-tab3">
                                    Tab content 3
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
<jsp:include page="../include/footer.jsp" />