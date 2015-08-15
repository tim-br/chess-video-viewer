class CreateUserTable < ActiveRecord::Migration
  def change
    create_table :users do |t|
      t.string :full_name
      t.boolean :is_admin
      t.string :email
      t.string :password
      t.string :password_digest, :string
      t.timestamps null: false
    end
  end
end
