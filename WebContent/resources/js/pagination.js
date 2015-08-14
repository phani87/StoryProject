/**
 * 
 */

/*$(document).ready(
		function() {
			$('#storyTable').after('<div class="page"><ul id="nav"></ul></div>');
			var rowsShown = 10;
			var rowsTotal = $('#storyTable tbody tr').length;
			var numPages = rowsTotal / rowsShown;
			for (i = 0; i < numPages; i++) {

				var pageNum = i + 1;
				$('#nav').append(
						'<li><a href="#" style="margin:auto" rel="' + i + '" >' + pageNum + '</a></li> ');
				$('#nav').addClass('pagination pagination-lg');

			}

			$('#storyTable tbody tr').hide();
			$('#storyTable tbody tr').slice(0, rowsShown).show();
			$('#nav a:first').addClass('pagination active');
			$('#nav a').bind(
					'click',
					function() {

						$('#nav a').removeClass('pagination active');
						$(this).addClass('pagination active');
						$(this).attr('style');
						var currPage = $(this).attr('rel');
						var startItem = currPage * rowsShown;
						var endItem = startItem + rowsShown;
						$('#storyTable tbody tr').css('opacity', '0.0').hide()
								.slice(startItem, endItem).css('display',
										'table-row').animate({
									opacity : 1
								}, 300);
					});
		});*/

$(document).ready(function() {
    $('#storyTable').dataTable( {
        "pagingType": "full_numbers"
    } );
} );

/*
 * $(document).ready(function() { $('#storyTable').dataTable({ "order": [[ 3,
 * "desc" ]] "language" : { "lengthMenu" : "Display _MENU_ records per page",
 * "zeroRecords" : "Nothing found - sorry", "info" : "Showing page _PAGE_ of
 * _PAGES_", "infoEmpty" : "No records available", "infoFiltered" : "(filtered
 * from _MAX_ total records)" } }); });
 */