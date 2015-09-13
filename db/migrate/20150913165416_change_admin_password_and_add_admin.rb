class ChangeAdminPasswordAndAddAdmin < ActiveRecord::Migration
  def change
 	 User.find_by(full_name: 'Jacob Stein').update_attributes(password: 'jacobadmin', password_confirmation: 'jacobadmin')
 	 User.create full_name: "Artiom", email: "samsonkin@list.ru", password: "artiomadmin", password_confirmation: "artiomadmin", is_admin: "true"
  end
end
